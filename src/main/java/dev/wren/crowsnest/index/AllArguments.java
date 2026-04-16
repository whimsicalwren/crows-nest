package dev.wren.crowsnest.index;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import dev.wren.crowsnest.internal.argument.ArgumentRegistry;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;

public class AllArguments {

    public static void register() {
        ArgumentRegistry.registerArgumentType(Vec3.class, Vec3Argument.class, Vec3Argument::vec3, Vec3Argument::getVec3);
        ArgumentRegistry.registerArgumentType(Double.class, DoubleArgumentType.class, DoubleArgumentType::doubleArg, DoubleArgumentType::getDouble);
        ArgumentRegistry.registerArgumentType(Float.class, FloatArgumentType.class, FloatArgumentType::floatArg, FloatArgumentType::getFloat);
        ArgumentRegistry.registerArgumentType(Integer.class, IntegerArgumentType.class, IntegerArgumentType::integer, IntegerArgumentType::getInteger);
        ArgumentRegistry.registerArgumentType(Long.class, LongArgumentType.class, LongArgumentType::longArg, LongArgumentType::getLong);
    }

}
