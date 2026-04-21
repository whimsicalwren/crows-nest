package dev.wren.crowsnest.index;

import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import org.joml.*;

import dev.wren.crowsnest.internal.formatting.ConverterRegistry;

public class AllConverters {

    public static void register() {
        ConverterRegistry.registerCast(Matrix4dc.class, Matrix4d.class);
        ConverterRegistry.registerCast(Matrix3dc.class, Matrix3d.class);
        ConverterRegistry.registerCast(Quaterniondc.class, Quaterniond.class);
        ConverterRegistry.registerCast(Vector3dc.class, Vector3d.class);
        ConverterRegistry.registerCast(Pose3dc.class, Pose3d.class);
    }

}
