package dev.wren.crowsnest.registries;

import dev.wren.crowsnest.internal.reg.TypeBranchRegistry;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import static dev.wren.crowsnest.CrowsNest.LOGGER;

public class TypeBranches {

    public static void register() {
        LOGGER.info("Registering type branches...");
        TypeBranchRegistry.registerAdapter(Vec3.class, node -> {
            node.commandNode("x", Vec3::x).typeAdapter(Double.class);
            node.commandNode("y", Vec3::y).typeAdapter(Double.class);
            node.commandNode("z", Vec3::z).typeAdapter(Double.class);
            node.commandNode("length", Vec3::length).typeAdapter(Double.class);
        });

        TypeBranchRegistry.registerAdapter(Vector3dc.class, node -> {
            node.commandNode("x", Vector3dc::x).typeAdapter(Double.class);
            node.commandNode("y", Vector3dc::y).typeAdapter(Double.class);
            node.commandNode("z", Vector3dc::z).typeAdapter(Double.class);
            node.commandNode("length", Vector3dc::length).typeAdapter(Double.class);
        });

        TypeBranchRegistry.registerAdapter(Quaterniondc.class, node -> {
            node.commandNode("x", Quaterniondc::x).typeAdapter(Double.class);
            node.commandNode("y", Quaterniondc::y).typeAdapter(Double.class);
            node.commandNode("z", Quaterniondc::z).typeAdapter(Double.class);
            node.commandNode("w", Quaterniondc::w).typeAdapter(Double.class);
        });

        TypeBranchRegistry.registerAdapter(AABB.class, node -> {
            node.commandNode("center", AABB::getCenter).typeAdapter(Vec3.class);
        });
    }
}
