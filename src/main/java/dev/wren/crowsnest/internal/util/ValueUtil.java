package dev.wren.crowsnest.internal.util;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import org.valkyrienskies.core.api.ships.LoadedShip;

public class ValueUtil {

    private static final ThreadLocal<Object> CURRENT = new ThreadLocal<>();

    public static void set(Object value) {
        CURRENT.set(value);
    }

    public static Object get() {
        return CURRENT.get();
    }

    public static <T> T getAs(Class<T> type) {
        return type.cast(get());
    }

    public static void clear() {
        CURRENT.remove();
    }

    public static CommandSourceStack setToShip(CommandContext<CommandSourceStack> ctx) {
        BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
        LoadedShip ship = Util.getShipAtPos(ctx.getSource().getUnsidedLevel(), pos);

        ValueUtil.set(ship);

        return ctx.getSource();
    }
}
