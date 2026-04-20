package dev.wren.crowsnest.index;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.wren.crowsnest.internal.argument.types.*;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

import java.util.function.Supplier;

public class AllArgumentTypes {

    public static void register() {
        argument(Vector3dArgument.class, Vector3dArgument::vector3d);
        argument(Vector3fArgument.class, Vector3fArgument::vector3f);
        argument(Vector4dArgument.class, Vector4dArgument::vector4d);
        argument(Vector4fArgument.class, Vector4fArgument::vector4f);
        argument(QuaterniondArgument.class, QuaterniondArgument::quaterniond);
        argument(QuaternionfArgument.class, QuaternionfArgument::quaternionf);
    }

    private static <A extends ArgumentType<?>> void argument(Class<A> argumentTypeClass, Supplier<A> argumentTypeSupplier) {
        ArgumentTypeInfos.registerByClass(argumentTypeClass, SingletonArgumentInfo.contextFree(argumentTypeSupplier));
    }

}
