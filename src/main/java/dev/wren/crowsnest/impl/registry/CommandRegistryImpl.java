package dev.wren.crowsnest.impl.registry;

import dev.wren.crowsnest.internal.registries.CommandRegistry;
import dev.wren.crowsnest.internal.registries.ConverterRegistry;
import net.minecraft.world.phys.AABB;
import org.joml.primitives.AABBdc;
import org.joml.primitives.AABBic;
import org.valkyrienskies.core.api.ships.LoadedShip;

public class CommandRegistryImpl {

    public static void register() {
        CommandRegistry.node(LoadedShip.class)
                .command("id", LoadedShip::getId)
                .command("slug", LoadedShip::getSlug)
                .command("shipAABB", AABB.class,ship -> ConverterRegistry.convert(ship.getShipAABB(), AABBic.class))
                .command("worldAABB", AABB.class, ship -> ConverterRegistry.convert(ship.getWorldAABB(), AABBdc.class))
        ;

        CommandRegistry.node(AABB.class)
                .command("minX", aabb -> aabb.minX)
        ;
    }

}
