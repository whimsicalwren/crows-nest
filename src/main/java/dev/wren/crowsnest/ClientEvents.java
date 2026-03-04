package dev.wren.crowsnest;

import dev.wren.crowsnest.commands.AllCommands;
import dev.wren.crowsnest.registries.TypeBranches;
import dev.wren.crowsnest.registries.TypeFormatters;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static dev.wren.crowsnest.CrowsNest.LOGGER;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        LOGGER.info("Registering type branches...");
        TypeBranches.register();
        LOGGER.info("Registering type formatters...");
        TypeFormatters.register();
        LOGGER.info("Registering commands...");
        AllCommands.register(event);
    }

}
