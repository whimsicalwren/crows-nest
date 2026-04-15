package dev.wren.crowsnest.impl.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix4d;
import org.joml.Quaterniond;

import dev.wren.crowsnest.internal.registries.FormatRegistry;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.bodies.properties.BodyTransformImpl;
import org.valkyrienskies.core.impl.chunk_tracking.ShipActiveChunksSet;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;

import static dev.wren.crowsnest.internal.util.FormatUtil.*;

public class FormatRegistryImpl {

    public static void register() {
        FormatRegistry.registerFormat(ChunkClaimImpl.class, (cc, builder) ->
                builder.format("Start: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXStart(), cc.getZStart()))
                        .format(NEWLINE)
                        .format("Middle: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXMiddle(), cc.getZMiddle()))
                        .format(NEWLINE)
                        .format("End: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXEnd(), cc.getXEnd()))
                        .format(NEWLINE)
                        .format("Index: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXIndex(), cc.getZIndex()))
                        .build()
        );

        FormatRegistry.registerFormat(BodyKinematicsImpl.class, (bk, builder) ->
                builder.format("Position: ", ChatFormatting.WHITE)
                        .format(formatXYZ(bk.getPosition()))
                        .format(NEWLINE)
                        .format("Rotation: ", ChatFormatting.WHITE)
                        .format(formatQuaternion(bk.getRotation()))
                        .format(NEWLINE)
                        .format("Velocity: ", ChatFormatting.WHITE)
                        .format(formatVec3(bk.getVelocity()))
                        .format(NEWLINE)
                        .format("Angular Velocity: ", ChatFormatting.WHITE)
                        .format(formatVec3(bk.getAngularVelocity()))
                        .format(NEWLINE)
                        .format("Scaling: ",  ChatFormatting.WHITE)
                        .format(formatXYZ(bk.getScaling()))
                        .build()
        );

        FormatRegistry.registerFormat(BodyTransformImpl.class, (bt, builder) ->
                builder.format("Position: ", ChatFormatting.WHITE)
                        .format(formatXYZ(bt.getPosition()))
                        .format(NEWLINE)
                        .format("Rotation: ", ChatFormatting.WHITE)
                        .format(formatQuaternion(bt.getRotation()))
                        .format(NEWLINE)
                        .format("Scaling: ", ChatFormatting.WHITE)
                        .format(formatXYZ(bt.getScaling()))
                        .build()
        );

        FormatRegistry.registerFormat(ShipActiveChunksSet.class, (acs, builder) ->
                builder.format("Size: ", ChatFormatting.WHITE)
                        .format(String.valueOf(acs.getSize()), ChatFormatting.YELLOW)
                        .build()
        );

        FormatRegistry.registerFormat(AABB.class, ((aabb, builder) ->
                builder.format("Min: ", ChatFormatting.WHITE)
                        .format(formatXYZPosition(aabb.minX, aabb.minY, aabb.minZ))
                        .format(NEWLINE)
                        .format("Max: ", ChatFormatting.WHITE)
                        .format(formatXYZPosition(aabb.maxX, aabb.maxY, aabb.maxZ))
                        .format(NEWLINE)
                        .format("Size: ", ChatFormatting.WHITE)
                        .format(formatXYZ(aabb.getXsize(), aabb.getYsize(), aabb.getZsize()))
                        .format(NEWLINE)
                        .format("Volume: ")
                        .format(formatNumber(aabb.getXsize() * aabb.getYsize() * aabb.getZsize()), ChatFormatting.YELLOW)
                        .build()
        ));

        FormatRegistry.registerFormat(Vec3.class, ((vec3, builder) ->
                builder.format(formatVec3(vec3)).build()
        ));

        FormatRegistry.registerFormat(Quaterniond.class, (qdc, builder) ->
                builder.format(formatQuaternion(qdc)).build()
        );

        FormatRegistry.registerFormat(Matrix4d.class, (m4d, builder) ->
                builder.format(SPLIT)
                        .format(m4d.m00(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m10(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m20(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m30(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(NEWLINE)
                        .format(SPLIT)
                        .format(m4d.m01(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m11(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m21(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m31(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(NEWLINE)
                        .format(SPLIT)
                        .format(m4d.m02(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m12(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m22(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m32(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(NEWLINE)
                        .format(SPLIT)
                        .format(m4d.m03(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m13(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m23(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .format(m4d.m33(), 8, ChatFormatting.WHITE).format(SPLIT)
                        .build()
        );
    }
}
