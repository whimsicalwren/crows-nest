package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import dev.wren.crowsnest.internal.registries.CommandRegistry;
import dev.wren.crowsnest.internal.util.Util;
import dev.wren.crowsnest.internal.util.ThreadValue;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;

import net.minecraft.core.BlockPos;
import org.valkyrienskies.core.api.ships.LoadedShip;

/**
 * it can't be <i>that</i> hard, can it?
 * <a href="https://tenor.com/view/clueless-gif-24395495">:clueless:</a>
 */
public class ShipInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("ship")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .redirect(CommandRegistry.getBuiltNode(LoadedShip.class), ctx -> {
                    BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
                    LoadedShip ship = Util.getShipAtPos(ctx.getSource().getUnsidedLevel(), pos);

                    ThreadValue.set(ship);

                    return ctx.getSource();
                })
            );
    }

}