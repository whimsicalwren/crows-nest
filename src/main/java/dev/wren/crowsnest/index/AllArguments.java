package dev.wren.crowsnest.index;

import com.mojang.brigadier.arguments.*;
import dev.wren.crowsnest.internal.argument.ArgumentRegistry;
import dev.wren.crowsnest.internal.argument.types.Vector3dArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class AllArguments {

    public static void register() {
        ArgumentRegistry.registerArgumentType(Vec3.class, Vec3Argument.class, Vec3Argument::vec3, Vec3Argument::getVec3);
        ArgumentRegistry.registerArgumentType(Vector3d.class, Vector3dArgument.class, Vector3dArgument::vector3d, Vector3dArgument::getVector3d);
        ArgumentRegistry.registerArgumentType(Vector3dc.class, Vector3dArgument.class, Vector3dArgument::vector3d, Vector3dArgument::getVector3d);
        ArgumentRegistry.registerArgumentType(Double.class, DoubleArgumentType.class, DoubleArgumentType::doubleArg, DoubleArgumentType::getDouble);
        ArgumentRegistry.registerArgumentType(Float.class, FloatArgumentType.class, FloatArgumentType::floatArg, FloatArgumentType::getFloat);
        ArgumentRegistry.registerArgumentType(Integer.class, IntegerArgumentType.class, IntegerArgumentType::integer, IntegerArgumentType::getInteger);
        ArgumentRegistry.registerArgumentType(Long.class, LongArgumentType.class, LongArgumentType::longArg, LongArgumentType::getLong);
        ArgumentRegistry.registerArgumentType(Boolean.class, BoolArgumentType.class, BoolArgumentType::bool, BoolArgumentType::getBool);
    }

}
