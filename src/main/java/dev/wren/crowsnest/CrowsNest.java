package dev.wren.crowsnest;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.wren.crowsnest.commands.ClientSubLevelInfoCommand;
import dev.wren.crowsnest.commands.CrowsNestCommands;
import dev.wren.crowsnest.commands.SubLevelInfoCommand;
import dev.wren.crowsnest.index.*;
import dev.wren.crowsnest.internal.command.CommandRegistry;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CrowsNest.MODID)
@SuppressWarnings("unused")
public class CrowsNest {
    public static final String NAME = "Crow's Nest";
    public static final String MODID = "crowsnest";
    public static final Logger LOGGER = LogManager.getLogger();

    public CrowsNest(IEventBus modEventBus, ModContainer modContainer) {
        Dist dist = FMLLoader.getDist();
        AllArgumentTypes.register();
        AllArguments.register();
        AllConverters.register();
        AllCommands.register(dist);
        AllFormats.register();

        CommandRegistry.buildAll();

        //NeoForge.EVENT_BUS.addListener(CrowsNest::registerCommands);

        if (dist == Dist.CLIENT) CrowsNestClient.init();

        LOGGER.info("{} ({}) initialized!", NAME, MODID);
    }

//    public static void registerCommands(RegisterCommandsEvent event) {
//        CrowsNestCommands.register(event.getDispatcher());
//    }

}
