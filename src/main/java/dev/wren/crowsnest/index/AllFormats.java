package dev.wren.crowsnest.index;

import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.sublevel.plot.LevelPlot;
import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Quaterniond;

import dev.wren.crowsnest.internal.formatting.FormatRegistry;

import java.util.ArrayList;

import static dev.wren.crowsnest.internal.util.FormatUtil.*;

public class AllFormats {

    @SuppressWarnings("unchecked")
    public static void register() {
        registerSableTypes();

        FormatRegistry.registerFormat(AABB.class, ((aabb, builder) ->
                builder.format("Min: ", ChatFormatting.WHITE)
                        .format(formatXYZPosition(aabb.minX, aabb.minY, aabb.minZ))
                        .newline()
                        .format("Max: ", ChatFormatting.WHITE)
                        .format(formatXYZPosition(aabb.maxX, aabb.maxY, aabb.maxZ))
                        .newline()
                        .format("Size: ", ChatFormatting.WHITE)
                        .format(formatXYZ(aabb.getXsize(), aabb.getYsize(), aabb.getZsize()))
                        .newline()
                        .format("Volume: ")
                        .format(formatNumber(aabb.getXsize() * aabb.getYsize() * aabb.getZsize()), ChatFormatting.YELLOW)
                        .build()
        ));

        FormatRegistry.registerFormat(Vec3.class, ((vec3, builder) ->
                builder.format(formatVec3(vec3)).build()
        ));

        FormatRegistry.registerFormat(Quaterniond.class, (qd, builder) ->
                builder.format(formatQuaternion(qd)).build()
        );

        FormatRegistry.registerFormat(Matrix4d.class, (m4d, builder) ->
                builder.format(SPLIT)
                        .format(m4d.m00(), ChatFormatting.RED).format(SPLIT)
                        .format(m4d.m10(), ChatFormatting.YELLOW).format(SPLIT)
                        .format(m4d.m20(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m4d.m30(), ChatFormatting.BLUE).format(SPLIT)
                        .newline()
                        .format(SPLIT)
                        .format(m4d.m01(), ChatFormatting.RED).format(SPLIT)
                        .format(m4d.m11(), ChatFormatting.YELLOW).format(SPLIT)
                        .format(m4d.m21(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m4d.m31(), ChatFormatting.BLUE).format(SPLIT)
                        .newline()
                        .format(SPLIT)
                        .format(m4d.m02(), ChatFormatting.RED).format(SPLIT)
                        .format(m4d.m12(), ChatFormatting.YELLOW).format(SPLIT)
                        .format(m4d.m22(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m4d.m32(), ChatFormatting.BLUE).format(SPLIT)
                        .newline()
                        .format(SPLIT)
                        .format(m4d.m03(), ChatFormatting.RED).format(SPLIT)
                        .format(m4d.m13(), ChatFormatting.YELLOW).format(SPLIT)
                        .format(m4d.m23(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m4d.m33(), ChatFormatting.BLUE).format(SPLIT)
                        .build()
        );

        FormatRegistry.registerFormat(Matrix3d.class, (m3d, builder) ->
                builder.format(SPLIT)
                        .format(m3d.m00(), ChatFormatting.RED).format(SPLIT)
                        .format(m3d.m10(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m3d.m20(), ChatFormatting.BLUE).format(SPLIT)
                        .newline()
                        .format(SPLIT)
                        .format(m3d.m01(), ChatFormatting.RED).format(SPLIT)
                        .format(m3d.m11(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m3d.m21(), ChatFormatting.BLUE).format(SPLIT)
                        .newline()
                        .format(SPLIT)
                        .format(m3d.m02(), ChatFormatting.RED).format(SPLIT)
                        .format(m3d.m12(), ChatFormatting.GREEN).format(SPLIT)
                        .format(m3d.m22(), ChatFormatting.BLUE).format(SPLIT)
                        .build()
        );

        FormatRegistry.registerFormat(ArrayList.class, (arr, builder) -> {
            arr.forEach(e ->
                    builder.format(FormatRegistry.format(e))
                            .newline()
                            .format("----------", ChatFormatting.WHITE)
                            .newline()
            );

            return builder.build();
        });

        FormatRegistry.registerFormat(Pose3d.class, (p3d, builder) ->
                builder.format("Position: ", ChatFormatting.WHITE)
                        .format(formatVec3(p3d.position()))
                        .format("Scale: ", ChatFormatting.WHITE)
                        .format(formatVec3(p3d.scale()))
                        .format("Orientation: ", ChatFormatting.WHITE)
                        .format(formatQuaternion(p3d.orientation()))
                        .build()
        );

        FormatRegistry.registerFormat(BoundingBox3d.class, (bb3d, builder) ->
                builder.format(FormatRegistry.format(bb3d.toMojang())).build()
        );

        FormatRegistry.registerFormat(LevelPlot.class, (lp, builder) ->
                builder.format("Center: ", ChatFormatting.WHITE)
                        .format(formatXYZ(lp.getCenterBlock()))
                        .build()
        );
    }

    public static void registerSableTypes() {

    }
}
