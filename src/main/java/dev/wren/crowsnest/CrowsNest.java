package dev.wren.crowsnest;

import dev.wren.crowsnest.impl.registry.CommandRegistryImpl;
import dev.wren.crowsnest.impl.registry.ConverterRegistryImpl;
import dev.wren.crowsnest.impl.registry.FormatRegistryImpl;
import dev.wren.crowsnest.internal.registries.CommandRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.wren.crowsnest.commands.CrowsNestCommands;

@Mod(CrowsNest.MODID)
@SuppressWarnings("unused")
public class CrowsNest {
    public static final String NAME = "Crow's Nest";
    public static final String MODID = "crowsnest";
    public static final Logger LOGGER = LogManager.getLogger();

    public CrowsNest(FMLJavaModLoadingContext context) {
        ConverterRegistryImpl.register();
        CommandRegistryImpl.register();
        FormatRegistryImpl.register();

        CommandRegistry.buildAll();

        MinecraftForge.EVENT_BUS.addListener(CrowsNestCommands::register);
        MinecraftForge.EVENT_BUS.addListener(CrowsNestCommands::registerClient);

        LOGGER.info("{} ({}) initialized!", NAME, MODID);
    }

}
