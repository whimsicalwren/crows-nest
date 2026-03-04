package dev.wren.crowsnest.internal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import org.valkyrienskies.core.api.ships.LoadedShip;

import java.util.Vector;
import java.util.function.Function;

import static dev.wren.crowsnest.internal.Utility.formatAABB;
import static dev.wren.crowsnest.internal.Utility.l;

public class CommandUtility {

    public static LiteralArgumentBuilder<CommandSourceStack> shipLiteral(String name, Function<LoadedShip, ?> func) {
        return Commands.literal(name).executes(ctx -> shipHandle(ctx, func, name));
    }

    public static int shipHandle(CommandContext<CommandSourceStack> context, Function<LoadedShip, ?> func, String name) {
        LoadedShip ship = Utility.getShipAtPos(context.getSource().getUnsidedLevel(), BlockPosArgument.getBlockPos(context, "pos"));

        if (ship == null) {
            context.getSource().sendFailure(Component.literal("No ship found!"));
            return 0;
        }

        Object result = func.apply(ship);

        context.getSource().sendSuccess(l("Found ship with " + name + " " + format(result)), false);

        return Command.SINGLE_SUCCESS;
    }

    public static String format(Object object) {
        if (object instanceof AABB aabb) { // todo replace with reg
            return formatAABB(aabb);
        }

        return String.valueOf(object);
    }

}
