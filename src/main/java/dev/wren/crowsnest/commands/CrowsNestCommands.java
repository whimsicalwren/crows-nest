package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import dev.wren.crowsnest.internal.util.ValueUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CrowsNestCommands {

    public static void registerClient(RegisterClientCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> clientRoot = Commands.literal("crowsnest")
            .then(ShipyardToWorldPosCommand.register())
            .then(ShipInfoCommand.register())
            .executes(ctx -> { try { return 1; } finally { ValueUtil.clear(); } }) // have fun reading this
            ;

        LiteralCommandNode<CommandSourceStack> clientRootNode = event.getDispatcher().register(clientRoot);

        event.getDispatcher().register(Commands.literal("cn").redirect(clientRootNode));
    }

    public static void register(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("crowsnestserver")
            .executes(ctx -> { try { return 1; } finally { ValueUtil.clear(); } })
            ;

        LiteralCommandNode<CommandSourceStack> rootNode = event.getDispatcher().register(root);

        event.getDispatcher().register(Commands.literal("cns").redirect(rootNode));
    }
}
