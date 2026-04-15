package dev.wren.crowsnest.internal.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.core.api.ships.LoadedShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;


public class Util {

    public static Long getShipIdAtPos(Level level, BlockPos pos) {
        LoadedShip loadedShip = VSGameUtilsKt.getLoadedShipManagingPos(level, pos);
        return loadedShip == null ? -1 : loadedShip.getId();
    }

    public static LoadedShip getShipAtPos(Level level, BlockPos pos) {
        return VSGameUtilsKt.getLoadedShipManagingPos(level, pos);
    }

    public static LoadedServerShip getServerShipAtPos(ServerLevel level, BlockPos pos) {
        return VSGameUtilsKt.getLoadedShipManagingPos(level, pos);
    }

    public static BlockPos getWorldPos(Level level, BlockPos pos) {
        return getWorldPos(level, pos, getShipIdAtPos(level, pos));
    }

    public static BlockPos getWorldPos(Level level, BlockPos pos, Long shipId) {
        Vector3d localPos = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
        if (shipId != null) {
            Ship shipObject = VSGameUtilsKt.getShipObjectWorld(level).getAllShips().getById(shipId);
            if (shipObject != null) {
                Vector3d worldPos = shipObject.getTransform().getShipToWorld().transformPosition(localPos, new Vector3d());
                return BlockPos.containing(worldPos.x, worldPos.y, worldPos.z);
            }
        }
        return BlockPos.containing(localPos.x, localPos.y, localPos.z);
    }
}
