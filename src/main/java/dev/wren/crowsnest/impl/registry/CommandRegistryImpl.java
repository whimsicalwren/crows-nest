package dev.wren.crowsnest.impl.registry;

import dev.wren.crowsnest.internal.registries.CommandRegistry;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.valkyrienskies.core.api.ships.LoadedShip;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.bodies.properties.BodyTransformImpl;
import org.valkyrienskies.core.impl.chunk_tracking.ShipActiveChunksSet;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;

public class CommandRegistryImpl {

    public static void register() {
        // vs stuff
        CommandRegistry.registerClass(LoadedShip.class);
        CommandRegistry.registerClass(BodyKinematicsImpl.class);
        CommandRegistry.registerClass(ChunkClaimImpl.class);
        CommandRegistry.registerClass(BodyTransformImpl.class);
        CommandRegistry.registerClass(ShipActiveChunksSet.class);

        // yeah
        CommandRegistry.registerClass(Matrix4d.class);
        CommandRegistry.registerClass(AABB.class);
        CommandRegistry.registerClass(Vec3.class);
        CommandRegistry.registerClass(Quaterniond.class);
    }

}
