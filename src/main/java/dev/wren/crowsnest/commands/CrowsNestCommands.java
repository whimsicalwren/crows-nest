package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;

public class CrowsNestCommands {

    public static void registerClient(RegisterClientCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> clientRoot = Commands.literal("crowsnest")
            .then(ShipyardToWorldPosCommand.register())
            .then(ShipInfoCommand.register())
            .then(ValueClearCommand.register())
            ;

        LiteralCommandNode<CommandSourceStack> clientRootNode = event.getDispatcher().register(clientRoot);

        event.getDispatcher().register(Commands.literal("cn").redirect(clientRootNode));
    }
}
