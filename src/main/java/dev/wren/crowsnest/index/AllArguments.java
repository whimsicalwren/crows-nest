package dev.wren.crowsnest.index;

import com.mojang.brigadier.arguments.*;
import dev.wren.crowsnest.internal.argument.ArgumentRegistry;
import dev.wren.crowsnest.internal.argument.types.*;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;
import org.joml.*;

public class AllArguments {

    public static void register() {
        ArgumentRegistry.registerArgumentType(Vector3dc.class, Vector3dArgument.class, Vector3dArgument::vector3d, Vector3dArgument::getVector3d);
        ArgumentRegistry.registerArgumentType(Vector3fc.class, Vector3fArgument.class, Vector3fArgument::vector3f, Vector3fArgument::getVector3f);
        ArgumentRegistry.registerArgumentType(Vector4dc.class, Vector4dArgument.class, Vector4dArgument::vector4d, Vector4dArgument::getVector4d);
        ArgumentRegistry.registerArgumentType(Vector4fc.class, Vector4fArgument.class, Vector4fArgument::vector4f, Vector4fArgument::getVector4f);
        ArgumentRegistry.registerArgumentType(Quaterniondc.class, QuaterniondArgument.class, QuaterniondArgument::quaterniond, QuaterniondArgument::getQuaterniond);
        ArgumentRegistry.registerArgumentType(Quaternionfc.class, QuaternionfArgument.class, QuaternionfArgument::quaternionf, QuaternionfArgument::getQuaternionf);
        ArgumentRegistry.registerArgumentType(Double.class, DoubleArgumentType.class, DoubleArgumentType::doubleArg, DoubleArgumentType::getDouble);
        ArgumentRegistry.registerArgumentType(Float.class, FloatArgumentType.class, FloatArgumentType::floatArg, FloatArgumentType::getFloat);
        ArgumentRegistry.registerArgumentType(Integer.class, IntegerArgumentType.class, IntegerArgumentType::integer, IntegerArgumentType::getInteger);
        ArgumentRegistry.registerArgumentType(Long.class, LongArgumentType.class, LongArgumentType::longArg, LongArgumentType::getLong);
        ArgumentRegistry.registerArgumentType(Boolean.class, BoolArgumentType.class, BoolArgumentType::bool, BoolArgumentType::getBool);
    }

}
