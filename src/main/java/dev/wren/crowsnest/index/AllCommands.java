package dev.wren.crowsnest.index;

import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.wren.crowsnest.internal.command.CommandRegistry;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.*;

public class AllCommands {

    public static void register(Dist dist) {
        // sable stuff
        CommandRegistry.registerClass(SubLevel.class);
        CommandRegistry.registerClass(Pose3d.class);
        CommandRegistry.registerClass(BoundingBox3d.class);

        // yeah
        CommandRegistry.registerClass(Matrix4d.class);
        CommandRegistry.registerClass(Matrix3d.class);
        CommandRegistry.registerClass(AABB.class);
        CommandRegistry.registerClass(Vector3d.class);
        CommandRegistry.registerClass(Vector4d.class);
        CommandRegistry.registerClass(Quaterniond.class);

        if (dist == Dist.CLIENT) {
            registerClient();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerClient() {
        CommandRegistry.registerClass(ClientSubLevel.class);
    }

}
