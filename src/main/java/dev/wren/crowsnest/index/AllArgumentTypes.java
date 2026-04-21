package dev.wren.crowsnest.index;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.wren.crowsnest.internal.argument.types.*;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

import java.util.function.Supplier;

public class AllArgumentTypes {

    public static void register() {
        a(Vector3dArgument.class, Vector3dArgument::vector3d);
        a(Vector3fArgument.class, Vector3fArgument::vector3f);
        a(Vector4dArgument.class, Vector4dArgument::vector4d);
        a(Vector4fArgument.class, Vector4fArgument::vector4f);
        a(QuaterniondArgument.class, QuaterniondArgument::quaterniond);
        a(QuaternionfArgument.class, QuaternionfArgument::quaternionf);
    }

    private static <A extends ArgumentType<?>> void a(Class<A> argumentTypeClass, Supplier<A> argumentTypeSupplier) {
        ArgumentTypeInfos.registerByClass(argumentTypeClass, SingletonArgumentInfo.contextFree(argumentTypeSupplier));
    }

}
