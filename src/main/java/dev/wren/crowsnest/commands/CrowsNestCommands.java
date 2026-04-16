package dev.wren.crowsnest.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import dev.wren.crowsnest.commands.client.ShipInfoCommand;
import dev.wren.crowsnest.commands.client.ShipyardToWorldPosCommand;
import dev.wren.crowsnest.commands.client.ValueClearCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CrowsNestCommands {

    public static void registerClient(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> clientRoot = Commands.literal("crowsnest")
                .then(ShipyardToWorldPosCommand.register())
                .then(ShipInfoCommand.register())
                .then(Vec3InfoCommand.register())
                .then(ValueClearCommand.register())
                ;

        LiteralCommandNode<CommandSourceStack> clientRootNode = dispatcher.register(clientRoot);

        dispatcher.register(Commands.literal("cn").redirect(clientRootNode));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("crowsnestserver")
                .then(ServerShipInfoCommand.register())
                ;

        LiteralCommandNode<CommandSourceStack> rootNode = dispatcher.register(root);

        dispatcher.register(Commands.literal("cns").redirect(rootNode));
    }
}
