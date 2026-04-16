package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.wren.crowsnest.internal.command.CommandRegistry;
import dev.wren.crowsnest.internal.util.ThreadValue;
import dev.wren.crowsnest.internal.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.core.api.ships.LoadedServerShip;

public class Vec3InfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("vec3")
            .then(Commands.argument("vec", Vec3Argument.vec3())
                .redirect(CommandRegistry.getBuiltNode(Vec3.class), ctx -> {
                    Vec3 vec3 = Vec3Argument.getVec3(ctx, "vec");

                    ThreadValue.set(vec3);

                    return ctx.getSource();
                })
            );
    }

}