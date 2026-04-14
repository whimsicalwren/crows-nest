package dev.wren.crowsnest.impl.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.Quaterniond;

import dev.wren.crowsnest.internal.registries.FormatRegistry;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;

import static dev.wren.crowsnest.internal.util.FormatUtil.*;

public class FormatRegistryImpl {

    public static void register() {
        FormatRegistry.registerFormatter(AABB.class, ((aabb, builder) ->
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

        FormatRegistry.registerFormatter(Vec3.class, ((vec3, builder) ->
                builder.format(formatVec3(vec3)).build()
        ));

        FormatRegistry.registerFormatter(Quaterniond.class, (qdc, builder) ->
                builder.format(formatQuaternion(qdc)).build()
        );

        FormatRegistry.registerFormatter(ChunkClaimImpl.class, (cc, builder) ->
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

        FormatRegistry.registerFormatter(BodyKinematicsImpl.class, (bk, builder) ->
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
    }
}
