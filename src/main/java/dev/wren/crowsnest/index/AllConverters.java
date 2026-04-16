package dev.wren.crowsnest.index;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.*;
import org.joml.primitives.AABBdc;
import org.joml.primitives.AABBic;

import dev.wren.crowsnest.internal.formatting.ConverterRegistry;
import org.valkyrienskies.core.api.bodies.properties.BodyKinematics;
import org.valkyrienskies.core.api.bodies.properties.BodyTransform;
import org.valkyrienskies.core.api.ships.DragController;
import org.valkyrienskies.core.api.ships.WingManager;
import org.valkyrienskies.core.api.ships.properties.ChunkClaim;
import org.valkyrienskies.core.api.ships.properties.ShipInertiaData;
import org.valkyrienskies.core.api.ships.properties.ShipTransform;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.bodies.properties.BodyTransformImpl;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;
import org.valkyrienskies.core.impl.game.ships.ShipInertiaDataImpl;
import org.valkyrienskies.core.impl.shadow.Eh;
import org.valkyrienskies.core.impl.shadow.Ew;

public class AllConverters {

    public static void register() {
        ConverterRegistry.registerConverter(Vector3dc.class, Vec3.class, v -> new Vec3(v.x(), v.y(), v.z()));
        ConverterRegistry.registerConverter(AABBic.class, AABB.class, box -> new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        ConverterRegistry.registerConverter(AABBdc.class, AABB.class, box -> new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        ConverterRegistry.registerCast(ChunkClaim.class, ChunkClaimImpl.class); // idk it makes it work
        ConverterRegistry.registerCast(BodyKinematics.class, BodyKinematicsImpl.class);
        ConverterRegistry.registerCast(ShipTransform.class, BodyTransformImpl.class);
        ConverterRegistry.registerCast(BodyTransform.class, BodyTransformImpl.class);
        ConverterRegistry.registerCast(Matrix4dc.class, Matrix4d.class);
        ConverterRegistry.registerCast(Matrix3dc.class, Matrix3d.class);
        ConverterRegistry.registerCast(Quaterniondc.class, Quaterniond.class);
        ConverterRegistry.registerCast(DragController.class, Eh.class);
        ConverterRegistry.registerCast(WingManager.class, Ew.class);
        ConverterRegistry.registerCast(ShipInertiaData.class, ShipInertiaDataImpl.class);
    }

}
