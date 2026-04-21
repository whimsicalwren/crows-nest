package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.wren.crowsnest.internal.command.CommandRegistry;
import dev.wren.crowsnest.internal.util.Util;
import dev.wren.crowsnest.internal.util.ValueSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;

public class SubLevelInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("sublevel")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .redirect(CommandRegistry.getBuiltNode(SubLevel.class), ctx -> {
                    BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
                    SubLevel subLevel = Util.getSubLevel(ctx.getSource().getUnsidedLevel(), pos);

                    return ctx.getSource().withSource(new ValueSource(subLevel, ctx.getSource().source));
                })
            );
    }

}