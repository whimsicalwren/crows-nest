package dev.wren.crowsnest.internal.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.wren.crowsnest.internal.formatting.ConverterRegistry;
import dev.wren.crowsnest.internal.argument.ArgumentMap;
import dev.wren.crowsnest.internal.argument.ArgumentRegistry;
import dev.wren.crowsnest.internal.util.ThreadValue;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public final class CommandRegistry {

    private static final Map<Class<?>, NodeBuilder<?>> BUILDERS = new HashMap<>();
    private static final Map<Class<?>, LiteralCommandNode<CommandSourceStack>> BUILT = new HashMap<>();

    public static LiteralCommandNode<CommandSourceStack> getBuiltNode(Class<?> type) {
        return BUILT.get(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> NodeBuilder<T> node(Class<T> type) {
        return (NodeBuilder<T>) BUILDERS.computeIfAbsent(type, NodeBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <T, CF, CT> void registerFields(Class<T> tClass) {
        NodeBuilder<T> tNodeBuilder = node(tClass);

        for (Field field : tClass.getFields()) {
            if (isInvalid(field)) continue;

            boolean sameType = tClass.equals(field.getType());

            if (sameType) {
                tNodeBuilder.command(field.getName(),
                    tClass,
                    (t, ctx) -> {
                        try {
                            return tClass.cast(field.get(t));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                continue;
            }

            Class<CF> resultType = (Class<CF>) field.getType();
            Class<CT> conversionResult = ConverterRegistry.getResultClass(resultType);

            tNodeBuilder.command(field.getName(),
                conversionResult,
                (t, ctx) -> {
                    try {
                        return ConverterRegistry.convert((CF) field.get(t), resultType);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, CF, CT> void registerNoParamMethods(Class<T> tClass) {
        NodeBuilder<T> tNodeBuilder = node(tClass);

        for (Method method : tClass.getMethods()) {
            if (!(method.getParameterCount() == 0)) continue;
            if (isInvalid(method)) continue;

            if (tClass.equals(method.getReturnType())) {
                tNodeBuilder.command(method.getName(),
                    tClass,
                    (t, ctx) -> {
                        try {
                            return tClass.cast(method.invoke(t));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
                continue;
            }

            Class<CF> resultType = (Class<CF>) method.getReturnType();
            Class<CT> conversionResult = ConverterRegistry.getResultClass(resultType);

            tNodeBuilder.command(method.getName(),
                conversionResult,
                (t, ctx) -> {
                try {
                    return ConverterRegistry.convert((CF) method.invoke(t), resultType);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, CF, CT> void registerMethods(Class<T> tClass) {
        NodeBuilder<T> tNodeBuilder = node(tClass);

        for (Method method : tClass.getMethods()) {
            if (method.getParameterCount() == 0) continue;
            if (isInvalid(method)) continue;
            ArgumentMap argumentTypes = ArgumentRegistry.getArgumentTypes(method);
            if (!argumentTypes.isValid()) continue;

            if (tClass.equals(method.getReturnType())) {
                tNodeBuilder.command(method.getName(),
                    tClass,
                    argumentTypes,
                    (t, ctx) -> {
                        try {
                            Object[] args = argumentTypes.getArguments(ctx);
                            return tClass.cast(method.invoke(t, args));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                });
                continue;
            }

            Class<CF> resultType = (Class<CF>) method.getReturnType();
            Class<CT> conversionResult = ConverterRegistry.getResultClass(resultType);

            tNodeBuilder.command(method.getName(),
                conversionResult,
                argumentTypes,
                (t, ctx) -> {
                    try {
                        Object[] args = argumentTypes.getArguments(ctx);
                        return ConverterRegistry.convert((CF) method.invoke(t, args), resultType);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
            });
        }
    }

    private static boolean isInvalid(Method method) {
        final int modifiers = method.getModifiers();
        return (!Modifier.isPublic(modifiers))
                || Modifier.isStatic(modifiers)
                || method.isSynthetic()
                || isBlacklisted(method)
                || isDeprecated(method);
    }

    private static boolean isInvalid(Field field) {
        final int modifiers = field.getModifiers();
        return (!Modifier.isPublic(modifiers))
                || Modifier.isStatic(modifiers)
                || isBlacklisted(field)
                || field.isAnnotationPresent(kotlin.Deprecated.class)
                || field.isAnnotationPresent(Deprecated.class);
    }


    private static final List<String> nameBlackList = List.of("getClass", "hashCode", "toString", "notifyAll", "notify", "wait", "equals", "CODEC", "codec");

    private static final List<String> nameAndClassBlacklist = List.of(
            "Eh.b", "Vec3#toVector3f", "LoadedShip#getTransformProvider", "BodyKinematicsImpl#toBuilder", "BodyTransformImpl#toBuilder"
    );

    private static final List<String> operatorCommands = List.of(
        "Eh#enableDrag", "Eh#disableDrag", "Eh#enableLift", "Eh#disableLift", "Eh#enableRotDrag", "Eh#disableRotDrag", "Ew#clearWingChanges", "Ew#getWingChanges"
    );

    private static boolean isBlacklisted(Method method) {
        return nameBlackList.contains(method.getName()) || nameAndClassBlacklist.contains(withClass(method));
    }

    private static boolean isBlacklisted(Field field) {
        return nameBlackList.contains(field.getName()) || nameAndClassBlacklist.contains(withClass(field));
    }

    private static String withClass(Field field) {
        return field.getDeclaringClass().getSimpleName() + "." + field.getName();
    }
    private static String withClass(Method method) {
        return method.getDeclaringClass().getSimpleName() + "#" + method.getName();
    }

    public static boolean isDeprecated(Method method) {
        if (method.isAnnotationPresent(Deprecated.class)) return true;
        if (method.isAnnotationPresent(kotlin.Deprecated.class)) return true;

        KClass<?> kClass = JvmClassMappingKt.getKotlinClass(method.getDeclaringClass());

        for (KProperty<?> prop : KClasses.getMemberProperties(kClass)) {
            Method getter = ReflectJvmMapping.getJavaGetter(prop);

            if (getter != null && getter.equals(method)) {
                return prop.getAnnotations().stream()
                        .anyMatch(a -> a.annotationType() == kotlin.Deprecated.class);
            }
        }

        return false;
    }

    public static <T> void registerClass(Class<T> tClass) {
        registerFields(tClass);
        registerNoParamMethods(tClass);
        registerMethods(tClass);
    }

    public static void buildAll() {
        BUILDERS.forEach((type, builder) -> {
            LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal(type.getSimpleName());

            BUILT.put(type, literal.build());
        });

        BUILDERS.forEach((type, builder) -> {
            LiteralCommandNode<CommandSourceStack> node = BUILT.get(type);

            for (CommandDef<?, ?> def : builder.getCommands()) {
                ArgumentBuilder<CommandSourceStack, ?> cmd = Commands.literal(def.name());

                String nameAndClass = def.sourceType().getSimpleName() + "#" + def.name();

                if (operatorCommands.contains(nameAndClass)) cmd.requires(ctx -> ctx.hasPermission(2));

                ArgumentBuilder<CommandSourceStack, ?> arguments = createArguments(def);

                if (arguments == null) {
                    if (def.returnType() != Void.class) attachRedirect(cmd, def);

                    attachExecutes(cmd, def);
                } else {
                    cmd.then(arguments);
                }

                node.addChild(cmd.build());
            }
        });
    }

    private static <T, R> ArgumentBuilder<CommandSourceStack, ?> createArguments(CommandDef<T, R> def) {
        List<Map.Entry<String, ArgumentType<?>>> entryList = new ArrayList<>(def.argumentMap().entrySet().stream().toList());
        if (entryList.isEmpty()) return null;
        Collections.reverse(entryList);
        ArgumentBuilder<CommandSourceStack, ?> toReturn;
        Iterator<Map.Entry<String, ArgumentType<?>>> itr = entryList.iterator();

        Map.Entry<String, ArgumentType<?>> lastEntry = itr.next();

        ArgumentBuilder<CommandSourceStack, ?> last = Commands.argument(lastEntry.getKey(), lastEntry.getValue());

        toReturn = attachExecutes(last, def);
        attachRedirect(toReturn, def);

        while (itr.hasNext()) {
            Map.Entry<String, ArgumentType<?>> nextEntry = itr.next();
            ArgumentBuilder<CommandSourceStack, ?> next = Commands.argument(nextEntry.getKey(), nextEntry.getValue());

            toReturn = next.then(toReturn);
        }

        return toReturn;
    }

    private static <T, R> void attachRedirect(ArgumentBuilder<CommandSourceStack, ?> command, CommandDef<T, R> def) {
        command.redirect(BUILT.get(def.returnType()), ctx -> {
                T value = ThreadValue.getAs(def.sourceType());

                R result = def.resultGetter().apply(value, ctx);

                ThreadValue.set(result);

                return ctx.getSource();
            }
        );
    }

    private static <T, R> ArgumentBuilder<CommandSourceStack, ?> attachExecutes(ArgumentBuilder<CommandSourceStack, ?> command, CommandDef<T, R> def) {
        return command.executes(ctx -> {
            T value = ThreadValue.getAs(def.sourceType());

            R result = def.resultGetter().apply(value, ctx);

            if (def.sender() != null) {
                def.sender().accept(result, ctx);
            }

            return 1;
        });
    }
}
