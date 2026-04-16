package dev.wren.crowsnest.index;

import dev.wren.crowsnest.internal.command.CommandRegistry;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.core.api.ships.LoadedShip;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.bodies.properties.BodyTransformImpl;
import org.valkyrienskies.core.impl.chunk_tracking.ShipActiveChunksSet;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;
import org.valkyrienskies.core.impl.game.ships.ShipInertiaDataImpl;
import org.valkyrienskies.core.impl.shadow.Eh;
import org.valkyrienskies.core.impl.shadow.Ew;

import java.util.ArrayList;

public class AllCommands {

    public static void register() {
        // vs stuff
        CommandRegistry.registerClass(LoadedShip.class);
        CommandRegistry.registerClass(BodyKinematicsImpl.class);
        CommandRegistry.registerClass(ChunkClaimImpl.class);
        CommandRegistry.registerClass(BodyTransformImpl.class);
        CommandRegistry.registerClass(ShipActiveChunksSet.class);

        // server ship stuff
        CommandRegistry.registerClass(LoadedServerShip.class);
        CommandRegistry.registerClass(Eh.class);
        CommandRegistry.registerClass(Ew.class);
        CommandRegistry.registerClass(ShipInertiaDataImpl.class);

        // yeah
        CommandRegistry.registerClass(Matrix4d.class);
        CommandRegistry.registerClass(Matrix3d.class);
        CommandRegistry.registerClass(AABB.class);
        CommandRegistry.registerClass(Vec3.class);
        CommandRegistry.registerMethods(Vec3.class);
        CommandRegistry.registerClass(Quaterniond.class);
        CommandRegistry.registerClass(ArrayList.class);
    }

}
