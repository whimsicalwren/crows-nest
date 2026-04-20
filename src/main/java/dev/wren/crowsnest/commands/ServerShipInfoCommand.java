package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.wren.crowsnest.internal.command.CommandRegistry;
import dev.wren.crowsnest.internal.util.Util;
import dev.wren.crowsnest.internal.util.ValueSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import org.valkyrienskies.core.api.ships.LoadedServerShip;

public class ServerShipInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("ship")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .redirect(CommandRegistry.getBuiltNode(LoadedServerShip.class), ctx -> {
                    BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
                    LoadedServerShip serverShip = Util.getServerShipAtPos(ctx.getSource().getLevel(), pos);

                    return ctx.getSource().withSource(new ValueSource(serverShip, ctx.getSource().source));
                })
            );
    }

}