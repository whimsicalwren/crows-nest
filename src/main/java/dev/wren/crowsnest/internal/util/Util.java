package dev.wren.crowsnest.internal.util;

import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3d;

import java.util.UUID;

public class Util {

    public static SubLevel getSubLevel(Level level, BlockPos pos) {
        return Sable.HELPER.getContaining(level, pos);
    }

    @OnlyIn(Dist.CLIENT)
    public static ClientSubLevel getClientSubLevel(BlockPos pos) {
        return Sable.HELPER.getContainingClient(pos);
    }

    public static BlockPos getWorldPos(Level level, BlockPos pos) {
        Vector3d localPos = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
        SubLevel subLevel = getSubLevel(level, pos);
        if (subLevel != null) {
            subLevel.logicalPose().transformPosition(localPos, localPos);
        }
        return BlockPos.containing(localPos.x, localPos.y, localPos.z);
    }
}
