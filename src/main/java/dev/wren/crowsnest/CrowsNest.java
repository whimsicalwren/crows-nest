package dev.wren.crowsnest;

import dev.wren.crowsnest.commands.CrowsNestCommands;
import dev.wren.crowsnest.index.AllArguments;
import dev.wren.crowsnest.index.AllCommands;
import dev.wren.crowsnest.index.AllConverters;
import dev.wren.crowsnest.index.AllFormats;
import dev.wren.crowsnest.internal.command.CommandRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CrowsNest.MODID)
@SuppressWarnings("unused")
public class CrowsNest {
    public static final String NAME = "Crow's Nest";
    public static final String MODID = "crowsnest";
    public static final Logger LOGGER = LogManager.getLogger();

    public CrowsNest(FMLJavaModLoadingContext context) {
        AllArguments.register();
        AllConverters.register();
        AllCommands.register();
        AllFormats.register();

        CommandRegistry.buildAll();

        MinecraftForge.EVENT_BUS.addListener(CrowsNest::registerCommands);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> CrowsNestClient::init);

        LOGGER.info("{} ({}) initialized!", NAME, MODID);
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        CrowsNestCommands.register(event.getDispatcher());
    }

}
