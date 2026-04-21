package dev.wren.crowsnest;

import dev.wren.crowsnest.commands.CrowsNestCommands;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;


public class CrowsNestClient {
    public static void init() {
        NeoForge.EVENT_BUS.addListener(CrowsNestClient::registerClientCommands);
    }

    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CrowsNestCommands.registerClient(event.getDispatcher());
    }
}
