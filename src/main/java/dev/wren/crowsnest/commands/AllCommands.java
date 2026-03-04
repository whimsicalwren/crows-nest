package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AllCommands {

    @SubscribeEvent
    public static void register(RegisterClientCommandsEvent event) {

        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("crowsnest")
            .then(ShipyardToWorldPosCommand.register())
            .then(ShipInfoCommand.register())
            ;

        event.getDispatcher().register(root);
    }

}
