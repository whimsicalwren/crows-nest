package dev.wren.crowsnest.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import dev.wren.crowsnest.internal.util.Util;

public class ShipyardToWorldPosCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("shipyardToWorldPos")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .executes(ctx -> {
                    BlockPos localPos = BlockPosArgument.getBlockPos(ctx, "pos");
                    BlockPos worldPos = Util.getWorldPos(ctx.getSource().getUnsidedLevel(), localPos);

                    ctx.getSource().sendSuccess(() -> Component.literal("Shipyard position " + formatBlockPos(localPos) + " translates to world position " + formatBlockPos(worldPos)), false);

                    return 1;
                })
            );
    }

    static String formatBlockPos(BlockPos pos) {
        return "("+pos.getX()+", "+pos.getY()+", "+pos.getZ()+")";
    }

}
