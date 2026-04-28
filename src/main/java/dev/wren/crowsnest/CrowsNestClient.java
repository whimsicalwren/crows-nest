package dev.wren.crowsnest;

import dev.wren.crowsnest.commands.CrowsNestCommands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = CrowsNest.MODID, dist = Dist.CLIENT)
@SuppressWarnings("unused")
public class CrowsNestClient {

    public CrowsNestClient(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(CrowsNestClient::registerClientCommands);
    }

    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CrowsNestCommands.registerClient(event.getDispatcher());
    }
}
