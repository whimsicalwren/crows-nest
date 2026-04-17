package dev.wren.crowsnest.index;

import dev.wren.crowsnest.internal.argument.types.Vector3dArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

public class AllArgumentTypes {

    public static void register() {
        ArgumentTypeInfos.registerByClass(
                Vector3dArgument.class,
                SingletonArgumentInfo.contextFree(Vector3dArgument::vector3d)
        );
    }

}
