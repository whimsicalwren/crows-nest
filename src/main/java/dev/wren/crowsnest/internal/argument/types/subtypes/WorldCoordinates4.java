package dev.wren.crowsnest.internal.argument.types.subtypes;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.wren.crowsnest.internal.argument.types.Vector4dArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WorldCoordinates4 implements Coordinates4 {

    private final WorldCoordinate x;
    private final WorldCoordinate y;
    private final WorldCoordinate z;
    private final WorldCoordinate w;

    public WorldCoordinates4(WorldCoordinate pX, WorldCoordinate pY, WorldCoordinate pZ, WorldCoordinate pW) {
        this.x = pX;
        this.y = pY;
        this.z = pZ;
        this.w = pW;
    }

    public Vec3 getPosition(CommandSourceStack pSource) {
        Vec3 vec3 = pSource.getPosition();
        return new Vec3(this.x.get(vec3.x), this.y.get(vec3.y), this.z.get(vec3.z));
    }

    public Vec2 getRotation(CommandSourceStack pSource) {
        Vec2 vec2 = pSource.getRotation();
        return new Vec2((float)this.x.get((double)vec2.x), (float)this.y.get((double)vec2.y));
    }

    public boolean isXRelative() {
        return this.x.isRelative();
    }

    public boolean isYRelative() {
        return this.y.isRelative();
    }

    public boolean isZRelative() {
        return this.z.isRelative();
    }

    public boolean isWRelative() {
        return this.w.isRelative();
    }

    public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else if (!(pOther instanceof WorldCoordinates4)) {
            return false;
        } else {
            WorldCoordinates4 worldcoordinates = (WorldCoordinates4)pOther;
            if (!this.x.equals(worldcoordinates.x)) {
                return false;
            } else {
                return this.y.equals(worldcoordinates.y) && this.z.equals(worldcoordinates.z);
            }
        }
    }

    public static WorldCoordinates4 parseInt(StringReader pReader) throws CommandSyntaxException {
        int i = pReader.getCursor();
        WorldCoordinate w0 = WorldCoordinate.parseInt(pReader);
        if (pReader.canRead() && pReader.peek() == ' ') {
            pReader.skip();
            WorldCoordinate w1 = WorldCoordinate.parseInt(pReader);
            if (pReader.canRead() && pReader.peek() == ' ') {
                pReader.skip();
                WorldCoordinate w2 = WorldCoordinate.parseInt(pReader);
                if (pReader.canRead() && pReader.peek() == ' ') {
                    pReader.skip();
                    WorldCoordinate w3 = WorldCoordinate.parseInt(pReader);
                    return new WorldCoordinates4(w0, w1, w2, w3);
                } else {
                    pReader.setCursor(i);
                    throw Vector4dArgument.ERROR_NOT_COMPLETE.createWithContext(pReader);
                }
            } else {
                pReader.setCursor(i);
                throw Vector4dArgument.ERROR_NOT_COMPLETE.createWithContext(pReader);
            }
        } else {
            pReader.setCursor(i);
            throw Vector4dArgument.ERROR_NOT_COMPLETE.createWithContext(pReader);
        }
    }

    public static WorldCoordinates4 parseDouble(StringReader pReader, boolean pCenterCorrect) throws CommandSyntaxException {
        int i = pReader.getCursor();
        WorldCoordinate w0 = WorldCoordinate.parseDouble(pReader, pCenterCorrect);
        if (pReader.canRead() && pReader.peek() == ' ') {
            pReader.skip();
            WorldCoordinate w1 = WorldCoordinate.parseDouble(pReader, false);
            if (pReader.canRead() && pReader.peek() == ' ') {
                pReader.skip();
                WorldCoordinate w2 = WorldCoordinate.parseDouble(pReader, pCenterCorrect);
                if (pReader.canRead() && pReader.peek() == ' ') {
                    pReader.skip();
                    WorldCoordinate w3 = WorldCoordinate.parseDouble(pReader, pCenterCorrect);
                    return new WorldCoordinates4(w0, w1, w2, w3);
                } else {
                    pReader.setCursor(i);
                    throw Vector4dArgument.ERROR_NOT_COMPLETE.createWithContext(pReader);
                }
            } else {
                pReader.setCursor(i);
                throw Vector4dArgument.ERROR_NOT_COMPLETE.createWithContext(pReader);
            }
        } else {
            pReader.setCursor(i);
            throw Vector4dArgument.ERROR_NOT_COMPLETE.createWithContext(pReader);
        }
    }

    public static WorldCoordinates4 absolute(double pX, double pY, double pZ, double pW) {
        return new WorldCoordinates4(new WorldCoordinate(false, pX), new WorldCoordinate(false, pY), new WorldCoordinate(false, pZ), new WorldCoordinate(false, pW));
    }

    public static WorldCoordinates4 absolute(Vec2 pVector) {
        return new WorldCoordinates4(new WorldCoordinate(false, (double)pVector.x), new WorldCoordinate(false, (double)pVector.y), new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D));
    }

    /**
     * A location with a delta of 0 for all values (equivalent to <code>~ ~ ~</code> or <code>~0 ~0 ~0</code>)
     */
    public static WorldCoordinates4 current() {
        return new WorldCoordinates4(new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D));
    }

    public int hashCode() {
        int i = this.x.hashCode();
        i = 31 * i + this.y.hashCode();
        i = 31 * i + this.z.hashCode();
        return 31 * i + this.w.hashCode();
    }

}
