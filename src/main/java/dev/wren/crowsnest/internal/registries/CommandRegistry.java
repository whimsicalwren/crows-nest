package dev.wren.crowsnest.internal.registries;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.wren.crowsnest.internal.util.ValueUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    }

    public record TypeCommandDef<T, R>(String name, Class<T> sourceType, Class<R> returnType, Function<T, R> resultGetter, BiConsumer<T, CommandSourceStackWrapper> sender) { }

    public static class TypeNodeBuilder<T> {

        private final Class<T> type;

        private final List<TypeCommandDef<T, ?>> commands = new ArrayList<>();

        public TypeNodeBuilder(Class<T> type) {
            this.type = type;
        }

        public <R> TypeNodeBuilder<T> command(String name, Class<R> returnType, Function<T, R> resultGetter) {
            return command(name, returnType, resultGetter, null);
        }

        public TypeNodeBuilder<T> command(String name, BiConsumer<T, CommandSourceStackWrapper> sender) {
            return command(name, Void.class, null, sender);
        }

        public <R> TypeNodeBuilder<T> command(String name, Function<T, R> contentGetter) {
            return command(name, Void.class, null, (t, stack) -> stack.send(contentGetter.apply(t)));
        }

        public <R> TypeNodeBuilder<T> command(String name, Class<R> returnType, Function<T, R> resultGetter, BiConsumer<T, CommandSourceStackWrapper> sender) {
            commands.add(new TypeCommandDef<>(name, type, returnType, resultGetter, sender));
            return this;
        }

        public List<TypeCommandDef<T, ?>> getCommands() {
            return commands;
        }
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
                System.out.println(value.toString());
                System.out.println(value.getClass());

                def.sender.accept(value, new CommandSourceStackWrapper(ctx.getSource()));
            }

            return 1;
        });
    }
}
