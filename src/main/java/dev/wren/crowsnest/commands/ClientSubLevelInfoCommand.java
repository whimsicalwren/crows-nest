package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import dev.wren.crowsnest.internal.command.CommandRegistry;
import dev.wren.crowsnest.internal.util.Util;
import dev.wren.crowsnest.internal.util.ValueSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;

import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * it can't be <i>that</i> hard, can it?
 * <a href="https://tenor.com/view/clueless-gif-24395495">:clueless:</a>
 */
@OnlyIn(Dist.CLIENT)
public class ClientSubLevelInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("clientsublevel")
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .redirect(CommandRegistry.getBuiltNode(ClientSubLevel.class), ctx -> {
                    BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
                    ClientSubLevel clientSublevel = Util.getClientSubLevel(pos);

                    return ctx.getSource().withSource(new ValueSource(clientSublevel, ctx.getSource().source));
                })
            );
    }

}