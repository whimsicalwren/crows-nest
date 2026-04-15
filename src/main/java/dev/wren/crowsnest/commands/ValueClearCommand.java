package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.wren.crowsnest.internal.util.ThreadValue;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ValueClearCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reset")
            .executes(ctx -> {
                ThreadValue.clear();

                ctx.getSource().sendSuccess(() -> Component.literal("ThreadValue reset"), false);

                return 1;
            });
    }

}
