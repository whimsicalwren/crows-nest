package dev.wren.crowsnest.internal.util;

import dev.wren.crowsnest.internal.registries.FormatRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.util.function.Supplier;

import static dev.wren.crowsnest.internal.registries.FormatRegistry.Format.of;

public class FormatUtil {

    public static FormatRegistry.Format[] formatVec3(Vec3 vec) {
        return new FormatRegistry.Format[]{
                of("X: ", ChatFormatting.WHITE),
                of(formatNumber(vec.x), ChatFormatting.RED),
                SEP,
                of("Y: ", ChatFormatting.WHITE),
                of(formatNumber(vec.y), ChatFormatting.GREEN),
                SEP,
                of("Z: ", ChatFormatting.WHITE),
                of(formatNumber(vec.z), ChatFormatting.BLUE),
                NEWLINE,
                of("Length: ", ChatFormatting.WHITE),
                of(formatNumber(vec.length()), ChatFormatting.YELLOW)
        };
    }

    public static FormatRegistry.Format[] formatVec3(Vector3dc vector3dc) {
        return formatVec3(new Vec3(vector3dc.x(), vector3dc.y(), vector3dc.z()));
    }

    public static FormatRegistry.Format[] formatQuaternion(Quaterniondc qdc) {
        return new FormatRegistry.Format[]{
                of("X: ", ChatFormatting.WHITE),
                of(formatNumber(qdc.x()), ChatFormatting.RED),
                SEP,
                of("Y: ", ChatFormatting.WHITE),
                of(formatNumber(qdc.y()), ChatFormatting.GREEN),
                SEP,
                of("Z: ", ChatFormatting.WHITE),
                of(formatNumber(qdc.z()), ChatFormatting.BLUE),
                SEP,
                of("W: ", ChatFormatting.WHITE),
                of(formatNumber(qdc.w()), ChatFormatting.GOLD),
                NEWLINE,
                of("Angle: ", ChatFormatting.WHITE),
                of(formatNumber(qdc.angle()), ChatFormatting.YELLOW)
        };
    }

    public static FormatRegistry.Format[] formatXYZ(Vec3 vec) {
        return formatXYZ(vec.x, vec.y, vec.z);
    }

    public static FormatRegistry.Format[] formatXYZ(Vector3dc vec) {
        return formatXYZ(vec.x(), vec.y(), vec.z());
    }

    public static FormatRegistry.Format[] formatXYZW(Quaterniondc qdc) {
        return formatXYZW(qdc.x(), qdc.y(), qdc.z(), qdc.w());
    }

    public static FormatRegistry.Format[] formatXYZ(double x, double y, double z) {
        return new FormatRegistry.Format[]{
                of("X: ", ChatFormatting.WHITE),
                of(formatNumber(x), ChatFormatting.RED),
                SEP,
                of("Y: ", ChatFormatting.WHITE),
                of(formatNumber(y), ChatFormatting.GREEN),
                SEP,
                of("Z: ", ChatFormatting.WHITE),
                of(formatNumber(z), ChatFormatting.BLUE)
        };
    }

    public static FormatRegistry.Format[] formatXYZW(double x, double y, double z, double w) {
        return new FormatRegistry.Format[]{
                of("X: ", ChatFormatting.WHITE),
                of(formatNumber(x), ChatFormatting.RED),
                SEP,
                of("Y: ", ChatFormatting.WHITE),
                of(formatNumber(y), ChatFormatting.GREEN),
                SEP,
                of("Z: ", ChatFormatting.WHITE),
                of(formatNumber(z), ChatFormatting.BLUE),
                SEP,
                of("W: ", ChatFormatting.WHITE),
                of(formatNumber(w), ChatFormatting.GOLD)
        };
    }

    public static FormatRegistry.Format[] formatXYZPosition(double x, double y, double z) {
        return new FormatRegistry.Format[]{
                of("(", ChatFormatting.WHITE),
                of(formatNumber(x), ChatFormatting.RED),
                SEP,
                of(formatNumber(y), ChatFormatting.GREEN),
                SEP,
                of(formatNumber(z), ChatFormatting.BLUE),
                of(")", ChatFormatting.WHITE)
        };
    }

    public static FormatRegistry.Format[] formatXZ(double x, double z) {
        return new FormatRegistry.Format[]{
                of("X: ", ChatFormatting.WHITE),
                of(formatNumber(x), ChatFormatting.RED),
                SEP,
                of("Z: ", ChatFormatting.WHITE),
                of(formatNumber(z), ChatFormatting.BLUE)
        };
    }

    public static FormatRegistry.Format[] formatXZPosition(double x, double z) {
        return new FormatRegistry.Format[]{
                of("(", ChatFormatting.WHITE),
                of(formatNumber(x), ChatFormatting.RED),
                SEP,
                of(formatNumber(z), ChatFormatting.BLUE),
                of(")", ChatFormatting.WHITE)
        };
    }

    public static FormatRegistry.Format NEWLINE = of("\n", ChatFormatting.WHITE);

    public static FormatRegistry.Format SEP = of(", ", ChatFormatting.WHITE);

    public static FormatRegistry.Format SPLIT = of(" | ", ChatFormatting.WHITE);

    private static final DecimalFormat NORMAL_FORMAT = new DecimalFormat("#,##0.###");

    private static final DecimalFormat SMALL_FORMAT = new DecimalFormat("0.#####");

    private static final DecimalFormat SCIENTIFIC_FORMAT = new DecimalFormat("0.###E0");

    public static String formatNumber(double value) {

        double abs = Math.abs(value);

        if (abs == 0) return "0";

        if (abs >= 1_000_000_000) {
            return SCIENTIFIC_FORMAT.format(value);
        }

        if (abs >= 0.001) {
            return NORMAL_FORMAT.format(value);
        }

        return SMALL_FORMAT.format(value);
    }

    public static Supplier<Component> l(Object literalString) {
        return () -> Component.literal(literalString.toString());
    }

    public static String forceLength(String base, int length) {
        int diff = base.length() - length;
        if (diff < 1) return base;
        String append = new String(new char[diff]).replace("\0", " ");
        return base + append;
    }

}
