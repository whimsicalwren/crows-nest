package dev.wren.crowsnest.impl.registry;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.*;
import org.joml.primitives.AABBdc;
import org.joml.primitives.AABBic;

import dev.wren.crowsnest.internal.registries.ConverterRegistry;
import org.valkyrienskies.core.api.bodies.properties.BodyKinematics;
import org.valkyrienskies.core.api.bodies.properties.BodyTransform;
import org.valkyrienskies.core.api.ships.properties.ChunkClaim;
import org.valkyrienskies.core.api.ships.properties.ShipTransform;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.bodies.properties.BodyTransformImpl;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;

public class ConverterRegistryImpl {

    public static void register() {
        ConverterRegistry.registerConverter(Vector3dc.class, Vec3.class, v -> new Vec3(v.x(), v.y(), v.z()));
        ConverterRegistry.registerConverter(AABBic.class, AABB.class, box -> new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        ConverterRegistry.registerConverter(AABBdc.class, AABB.class, box -> new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        ConverterRegistry.registerConverter(ChunkClaim.class, ChunkClaimImpl.class, ChunkClaimImpl.class::cast);
        ConverterRegistry.registerConverter(BodyKinematics.class, BodyKinematicsImpl.class, BodyKinematicsImpl.class::cast);
        ConverterRegistry.registerConverter(ShipTransform.class, BodyTransformImpl.class, BodyTransformImpl.class::cast);
        ConverterRegistry.registerConverter(BodyTransform.class, BodyTransformImpl.class, BodyTransformImpl.class::cast);
        ConverterRegistry.registerConverter(Matrix4dc.class, Matrix4d.class, Matrix4d.class::cast);
        ConverterRegistry.registerConverter(Quaterniondc.class, Quaterniond.class, Quaterniond.class::cast);
    }

}
