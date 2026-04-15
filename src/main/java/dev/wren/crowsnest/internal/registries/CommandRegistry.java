package dev.wren.crowsnest.internal.registries;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.wren.crowsnest.internal.util.ValueUtil;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class CommandRegistry {

    private static final Map<Class<?>, TypeNodeBuilder<?>> BUILDERS = new HashMap<>();
    private static final Map<Class<?>, LiteralCommandNode<CommandSourceStack>> BUILT = new HashMap<>();

    public static LiteralCommandNode<CommandSourceStack> getBuiltNode(Class<?> type) {
        return BUILT.get(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeNodeBuilder<T> node(Class<T> type) {
        return (TypeNodeBuilder<T>) BUILDERS.computeIfAbsent(type, TypeNodeBuilder::new);
    }

    public record CommandSourceStackWrapper(CommandSourceStack stack) {
        public void send(Object content) {
            stack.sendSuccess(() -> Component.literal(content.toString()), false);
        }

        public void send(Component content) {
            stack.sendSuccess(() -> content, false);
        }
    }

    public record TypeCommandDef<T, R>(String name, Class<T> sourceType, Class<R> returnType, Function<T, R> resultGetter, BiConsumer<T, CommandSourceStackWrapper> sender) { }

    public static class TypeNodeBuilder<T> {

        private final Class<T> type;

        private final List<TypeCommandDef<T, ?>> commands = new ArrayList<>();

        public TypeNodeBuilder(Class<T> type) {
            this.type = type;
        }

        public <R> TypeNodeBuilder<T> fromMethod(String name, Class<R> returnType, Function<T, R> getter) {
            return command(name, returnType, getter, (t, stack) -> stack.send(FormatRegistry.format(getter.apply(t))));
        }

        public <R> TypeNodeBuilder<T> fromField(String name, Function<T, R> getter) {
            return command(name, Void.class, null, (t, stack) -> stack.send(FormatRegistry.format(getter.apply(t))));
        }

        public <R> TypeNodeBuilder<T> command(String name, Class<R> returnType, Function<T, R> resultGetter, BiConsumer<T, CommandSourceStackWrapper> sender) {
            commands.add(new TypeCommandDef<>(name, type, returnType, resultGetter, sender));
            return this;
        }

        public List<TypeCommandDef<T, ?>> getCommands() {
            return commands;
        }
    }

    public static <T> void registerFields(Class<T> tClass) {
        TypeNodeBuilder<T> tNodeBuilder = node(tClass);

        for (Field field : tClass.getFields()) {
            if (!isFieldValid(field)) continue;

            tNodeBuilder.fromField(field.getName(), t -> {
                try {
                    return field.get(t);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, CF, CT> void registerNoParamMethods(Class<T> tClass) {
        TypeNodeBuilder<T> tNodeBuilder = node(tClass);

        System.out.println(tClass.getSimpleName());

        for (Method method : tClass.getMethods()) {
            if (!(method.getParameterCount() == 0)) continue;
            if (!isMethodValid(method)) continue;

            System.out.println(method.getName());
            System.out.println(Arrays.toString(method.getAnnotations()));

            boolean sameType = tClass.equals(method.getReturnType());

            if (sameType) {
                tNodeBuilder.fromMethod(method.getName(),
                    tClass,
                    t -> {
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

            tNodeBuilder.fromMethod(method.getName(),
                conversionResult,
                t -> {
                try {
                    return ConverterRegistry.convert((CF) method.invoke(t), resultType);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static boolean isMethodValid(Method method) {
        final int modifiers = method.getModifiers();
        return (Modifier.isPublic(modifiers))
                && !Modifier.isStatic(modifiers)
                && !method.isSynthetic()
                && !isBlacklisted(method)
                && !method.isAnnotationPresent(Deprecated.class)
                && !method.isAnnotationPresent(kotlin.Deprecated.class)
                && !isKotlinDeprecated(method);
    }

    private static boolean isFieldValid(Field field) {
        final int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers))
                && !Modifier.isStatic(modifiers)
                && !field.isAnnotationPresent(kotlin.Deprecated.class)
                && !field.isAnnotationPresent(Deprecated.class);
    }

    private static boolean isBlacklisted(Method method) {
        return methodNameBlacklist.contains(method.getName());
    }

    private static final List<String> methodNameBlacklist = List.of(
            "getClass", "hashCode", "toString", "notifyAll", "notify",
            "toBuilder", "wait", "toVector3f", "toLong");


    public static boolean isKotlinDeprecated(Method method) {
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
    }

    public static void buildAll() {

        BUILDERS.forEach((type, builder) -> {
            LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal(type.getSimpleName());

            BUILT.put(type, literal.build());
        });

        BUILDERS.forEach((type, builder) -> {

            LiteralCommandNode<CommandSourceStack> node = BUILT.get(type);

            for (TypeCommandDef<?, ?> def : builder.getCommands()) {

                LiteralArgumentBuilder<CommandSourceStack> cmd = Commands.literal(def.name);

                if (def.returnType != Void.class) attachRedirect(cmd, def);

                attachExecutes(cmd, def);

                node.addChild(cmd.build());
            }
        });
    }

    private static <T, R> void attachRedirect(LiteralArgumentBuilder<CommandSourceStack> command, TypeCommandDef<T, R> def) {
        command.redirect(BUILT.get(def.returnType), ctx -> {
            T value = ValueUtil.getAs(def.sourceType);

            R result = def.resultGetter.apply(value);

            ValueUtil.set(result);

            return ctx.getSource();
        });
    }

    private static <T, R> void attachExecutes(LiteralArgumentBuilder<CommandSourceStack> command, TypeCommandDef<T, R> def) {
        command.executes(ctx -> {
            T value = ValueUtil.getAs(def.sourceType);

            if (def.sender != null) {
                def.sender.accept(value, new CommandSourceStackWrapper(ctx.getSource()));
            }

            return 1;
        });
    }
}
