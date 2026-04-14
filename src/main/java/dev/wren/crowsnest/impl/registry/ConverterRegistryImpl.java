package dev.wren.crowsnest.impl.registry;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.Vector3dc;
import org.joml.primitives.AABBdc;
import org.joml.primitives.AABBic;

import dev.wren.crowsnest.internal.registries.ConverterRegistry;

public class ConverterRegistryImpl {

    public static void register() {
        ConverterRegistry.registerBridge(Vector3dc.class, Vec3.class, v -> new Vec3(v.x(), v.y(), v.z()));
        ConverterRegistry.registerBridge(AABBic.class, AABB.class, box -> new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        ConverterRegistry.registerBridge(AABBdc.class, AABB.class, box -> new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
    }

}
