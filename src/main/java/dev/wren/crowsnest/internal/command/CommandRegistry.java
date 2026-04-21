package dev.wren.crowsnest.internal.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.wren.crowsnest.internal.formatting.ConverterRegistry;
import dev.wren.crowsnest.internal.argument.ArgumentMap;
import dev.wren.crowsnest.internal.argument.ArgumentRegistry;
import dev.wren.crowsnest.internal.util.ValueSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

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
                || method.isAnnotationPresent(Deprecated.class);
    }

    private static boolean isInvalid(Field field) {
        final int modifiers = field.getModifiers();
        return (!Modifier.isPublic(modifiers))
                || Modifier.isStatic(modifiers)
                || isBlacklisted(field)
                || field.isAnnotationPresent(Deprecated.class);
    }


    private static final List<String> nameBlackList = List.of("getClass", "hashCode", "toString", "notifyAll", "notify", "wait", "equals", "CODEC", "codec", "tick");

    private static final List<String> nameAndClassBlacklist = List.of(

    );

    private static final List<String> operatorCommands = List.of(

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

            Map<String, ArgumentBuilder<CommandSourceStack, ?>> commands = new HashMap<>();

            for (CommandDef<?, ?> def : builder.getCommands()) {
                ArgumentBuilder<CommandSourceStack, ?> arguments = createArguments(def);

                ArgumentBuilder<CommandSourceStack, ?> cmd = commands.computeIfAbsent(def.name(), name -> createLiteral(name, def.sourceType().getSimpleName()));

                if (arguments == null) {
                    if (cmd.getArguments().isEmpty()) {
                        if (def.returnType() != Void.class) attachRedirect(cmd, def);

                        attachExecutes(cmd, def);
                    } else {
                        ArgumentBuilder<CommandSourceStack, ?> newCmd = commands.computeIfAbsent(def.name() + "_", name -> createLiteral(name, def.sourceType().getSimpleName()));
                        if (def.returnType() != Void.class) attachRedirect(newCmd, def);

                        attachExecutes(newCmd, def);
                    }
                } else {
                    if (cmd.getRedirect() == null) {
                        cmd.then(arguments);
                    } else {
                        ArgumentBuilder<CommandSourceStack, ?> newCmd = commands.computeIfAbsent(def.name() + "_", name -> createLiteral(name, def.sourceType().getSimpleName()));
                        newCmd.then(arguments);
                    }
                }
            }

            for (ArgumentBuilder<CommandSourceStack, ?> cmd : commands.values()) {
                node.addChild(cmd.build());
            }
        });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> createLiteral(String name, String className) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal(name);
        if (operatorCommands.contains(className + "#" + name)) command.requires(ctx -> ctx.hasPermission(2));
        return command;
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
                if (!(ctx.getSource().source instanceof ValueSource valueSource)) return ctx.getSource();
                T value = valueSource.getAs(def.sourceType());

                R result = def.resultGetter().apply(value, ctx);

                return ctx.getSource().withSource(valueSource.withResult(result));
            }
        );
    }

    private static <T, R> ArgumentBuilder<CommandSourceStack, ?> attachExecutes(ArgumentBuilder<CommandSourceStack, ?> command, CommandDef<T, R> def) {
        return command.executes(ctx -> {
            if (!(ctx.getSource().source instanceof ValueSource valueSource)) {
                ctx.getSource().sendFailure(Component.literal("Expected value of type ValueSource from context, instead got " + ctx.getSource().source.getClass().getSimpleName()));
                return 0;
            }

            T value = valueSource.getAs(def.sourceType());

            R result = def.resultGetter().apply(value, ctx);

            if (def.sender() != null) {
                def.sender().accept(result, ctx);
            }

            return 1;
        });
    }
}
