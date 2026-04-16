package dev.wren.crowsnest.internal.command;

import com.mojang.brigadier.context.CommandContext;
import dev.wren.crowsnest.internal.argument.ArgumentMap;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public record CommandDef<T, R>(
        String name,
        Class<T> sourceType,
        Class<R> returnType,
        ArgumentMap argumentMap,
        BiFunction<T, CommandContext<CommandSourceStack>, R> resultGetter,
        BiConsumer<R, CommandContext<CommandSourceStack>> sender
) {}
