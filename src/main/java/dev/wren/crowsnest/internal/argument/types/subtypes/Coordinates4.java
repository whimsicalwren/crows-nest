package dev.wren.crowsnest.internal.argument.types.subtypes;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public interface Coordinates4 {

    Vec3 getPosition(CommandSourceStack pSource);

    Vec2 getRotation(CommandSourceStack pSource);

    default BlockPos getBlockPos(CommandSourceStack pSource) {
        return BlockPos.containing(this.getPosition(pSource));
    }

    boolean isXRelative();

    boolean isYRelative();

    boolean isZRelative();

    boolean isWRelative();

    class Text {
        public static final Text DEFAULT_LOCAL = new Text("^", "^", "^", "^");
        public static final Text DEFAULT_GLOBAL = new Text("~", "~", "~", "~");
        public final String x;
        public final String y;
        public final String z;
        public final String w;

        public Text(String pX, String pY, String pZ, String pW) {
            this.x = pX;
            this.y = pY;
            this.z = pZ;
            this.w = pW;
        }
    }
}
