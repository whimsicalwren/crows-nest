package dev.wren.crowsnest.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CrowsNestCommands {

    @OnlyIn(Dist.CLIENT)
    public static void registerClient(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> clientRoot = Commands.literal("crowsnest")
                .then(ClientSubLevelInfoCommand.register())
                .then(SubLevelInfoCommand.register());

        LiteralCommandNode<CommandSourceStack> clientRootNode = dispatcher.register(clientRoot);
        dispatcher.register(Commands.literal("cn").redirect(clientRootNode));
    }
}
