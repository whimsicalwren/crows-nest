package dev.wren.crowsnest;

import dev.wren.crowsnest.internal.FormatUtility;
import dev.wren.crowsnest.registries.TypeFormatterRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static dev.wren.crowsnest.internal.FormatUtility.NEWLINE;

@Mod(CrowsNest.MODID)
public class CrowsNest {
    public static final String MODID = "crowsnest";

    public CrowsNest(FMLJavaModLoadingContext context) {
        registerTypeFormatters();
    }

    public void registerTypeFormatters() {
        TypeFormatterRegistry.registerFormatter(AABB.class, aabb ->
            Component.literal("Min: ")
                .append(FormatUtility.formatXYZ(aabb.minX, aabb.minY, aabb.minZ))
                .append(NEWLINE)
                .append(Component.literal("Max: "))
                .append(FormatUtility.formatXYZ(aabb.maxX, aabb.maxY, aabb.maxZ))
                .append(NEWLINE)
                .append(Component.literal("Size: "))
                .append(FormatUtility.formatXYZ(aabb.getXsize(), aabb.getYsize(), aabb.getZsize()))
                .append(NEWLINE)
                .append(Component.literal("Volume: "))
                .append(FormatUtility.formatNumber(aabb.getXsize() * aabb.getYsize() * aabb.getZsize()))
        );
    }
}
