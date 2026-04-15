package dev.wren.crowsnest;

import dev.wren.crowsnest.commands.CrowsNestCommands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;


public class CrowsNestClient {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(CrowsNestClient::registerClientCommands);
    }

    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CrowsNestCommands.registerClient(event.getDispatcher());
    }
}
