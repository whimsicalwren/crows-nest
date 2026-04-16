package dev.wren.crowsnest.internal.command;

import com.mojang.brigadier.context.CommandContext;
import dev.wren.crowsnest.internal.argument.ArgumentMap;
import dev.wren.crowsnest.internal.formatting.FormatRegistry;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NodeBuilder<T> {

    private final Class<T> type;

    private final List<CommandDef<T, ?>> commands = new ArrayList<>();

    public NodeBuilder(Class<T> type) {
        this.type = type;
    }

    public <R> NodeBuilder<T> command(String name, Class<R> returnType, BiFunction<T, CommandContext<CommandSourceStack>,  R> getter) {
        return command(name, returnType, ArgumentMap.empty(), getter, (r, stack) -> stack.getSource().sendSuccess(() -> FormatRegistry.format(r, name), false));
    }

    public <R> NodeBuilder<T> command(String name, Class<R> returnType, ArgumentMap argMap, BiFunction<T, CommandContext<CommandSourceStack>,  R> getter) {
        return command(name, returnType, argMap, getter, (r, stack) -> stack.getSource().sendSuccess(() -> FormatRegistry.format(r, name), false));
    }

    public <R> NodeBuilder<T> command(String name, Class<R> returnType, ArgumentMap argumentMap, BiFunction<T, CommandContext<CommandSourceStack>, R> resultGetter, BiConsumer<R, CommandContext<CommandSourceStack>> sender) {
        commands.add(new CommandDef<>(name, type, returnType, argumentMap, resultGetter, sender));
        return this;
    }

    public List<CommandDef<T, ?>> getCommands() {
        return commands;
    }
}