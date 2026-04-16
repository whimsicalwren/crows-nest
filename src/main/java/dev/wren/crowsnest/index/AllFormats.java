package dev.wren.crowsnest.index;

import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Quaterniond;

import dev.wren.crowsnest.internal.formatting.FormatRegistry;
import org.valkyrienskies.core.api.ships.DragController;
import org.valkyrienskies.core.api.ships.WingManager;
import org.valkyrienskies.core.impl.bodies.properties.BodyKinematicsImpl;
import org.valkyrienskies.core.impl.bodies.properties.BodyTransformImpl;
import org.valkyrienskies.core.impl.chunk_tracking.ShipActiveChunksSet;
import org.valkyrienskies.core.impl.game.ChunkClaimImpl;
import org.valkyrienskies.core.impl.game.ships.ShipInertiaDataImpl;
import org.valkyrienskies.mod.common.util.BuoyancyHandlerAttachment;
import org.valkyrienskies.mod.common.util.SplittingDisablerAttachment;

import java.util.ArrayList;

import static dev.wren.crowsnest.internal.util.FormatUtil.*;

public class AllFormats {

    public static void register() {
        registerVSTypes();

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
                            .format("----------")
                            .newline()
            );

            return builder.build();
        });
    }

    public static void registerVSTypes() {
        FormatRegistry.registerFormat(ChunkClaimImpl.class, (cc, builder) ->
                builder.format("Start: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXStart(), cc.getZStart()))
                        .newline()
                        .format("Middle: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXMiddle(), cc.getZMiddle()))
                        .newline()
                        .format("End: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXEnd(), cc.getXEnd()))
                        .newline()
                        .format("Index: ", ChatFormatting.WHITE)
                        .format(formatXZPosition(cc.getXIndex(), cc.getZIndex()))
                        .build()
        );

        FormatRegistry.registerFormat(BodyKinematicsImpl.class, (bk, builder) ->
                builder.format("Position: ", ChatFormatting.WHITE)
                        .format(formatXYZ(bk.getPosition()))
                        .newline()
                        .format("Rotation: ", ChatFormatting.WHITE)
                        .format(formatQuaternion(bk.getRotation()))
                        .newline()
                        .format("Velocity: ", ChatFormatting.WHITE)
                        .format(formatVec3(bk.getVelocity()))
                        .newline()
                        .format("Angular Velocity: ", ChatFormatting.WHITE)
                        .format(formatVec3(bk.getAngularVelocity()))
                        .newline()
                        .format("Scaling: ",  ChatFormatting.WHITE)
                        .format(formatXYZ(bk.getScaling()))
                        .build()
        );

        FormatRegistry.registerFormat(BodyTransformImpl.class, (bt, builder) ->
                builder.format("Position: ", ChatFormatting.WHITE)
                        .format(formatXYZ(bt.getPosition()))
                        .newline()
                        .format("Rotation: ", ChatFormatting.WHITE)
                        .format(formatQuaternion(bt.getRotation()))
                        .newline()
                        .format("Scaling: ", ChatFormatting.WHITE)
                        .format(formatXYZ(bt.getScaling()))
                        .build()
        );

        FormatRegistry.registerFormat(ShipActiveChunksSet.class, (acs, builder) ->
                builder.format("Size: ", ChatFormatting.WHITE)
                        .format(String.valueOf(acs.getSize()), ChatFormatting.YELLOW)
                        .build()
        );

        FormatRegistry.registerFormat(DragController.class, (eh, builder) ->
                builder.format("Drag: ", ChatFormatting.WHITE)
                        .format(formatVec3(eh.getDragForce()))
                        .format(NEWLINE)
                        .format("Lift: ", ChatFormatting.WHITE)
                        .format(formatVec3(eh.getLiftForce()))
                        .format(NEWLINE)
                        .format("Wind: ", ChatFormatting.WHITE)
                        .format(formatVec3(eh.getWindVector()))
                        .build()
        );

        FormatRegistry.registerFormat(WingManager.class, (ew, builder) ->
                builder.format("Id: ", ChatFormatting.WHITE).format(String.valueOf(ew.getFirstWingGroupId()), ChatFormatting.GOLD).build()
        );

        FormatRegistry.registerFormat(BuoyancyHandlerAttachment.class, (bh, builder) ->
                builder.format("Data: ", ChatFormatting.WHITE).format(FormatRegistry.format(bh.getBuoyancyData())).build()
        );

        FormatRegistry.registerFormat(SplittingDisablerAttachment.class, (sd, builder) ->
                builder.format("Splitting: ", ChatFormatting.WHITE).format(String.valueOf(sd.canSplit()), ChatFormatting.GOLD).build()
        );

        FormatRegistry.registerFormat(BuoyancyHandlerAttachment.BuoyancyData.class, (bd, builder) ->
                builder.format("Total Volume: ", ChatFormatting.WHITE)
                        .format(bd.getPocketVolumeTotal(), ChatFormatting.YELLOW)
                        .newline()
                        .format("Center Average: ", ChatFormatting.WHITE)
                        .format(formatVec3(bd.getPocketCenterAverage()))
                        .build()
        );

        FormatRegistry.registerFormat(ShipInertiaDataImpl.class, (id, builder) ->
                builder.format("Mass: ", ChatFormatting.WHITE)
                        .format(id.getMass(), ChatFormatting.GOLD)
                        .newline()
                        .format("COM: ", ChatFormatting.WHITE)
                        .format(formatVec3(id.getCenterOfMass()))
                        .newline()
                        .format("Inertia Tensor: ", ChatFormatting.WHITE)
                        .format(FormatRegistry.format(id.getInertiaTensor()))
                        .newline()
                        .format("Moment of Inertia Tensor: ", ChatFormatting.WHITE)
                        .format(FormatRegistry.format(id.getMomentOfInertiaTensorToSave()))
                        .build()
        );
    }
}
