package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import dev.wren.crowsnest.internal.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;

import net.minecraft.world.phys.AABB;
import org.joml.Matrix4dc;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.bodies.properties.BodyKinematics;
import org.valkyrienskies.core.api.bodies.properties.BodyTransform;
import org.valkyrienskies.core.api.ships.LoadedShip;

import static dev.wren.crowsnest.internal.CommandUtility.branchNode;
import static dev.wren.crowsnest.internal.CommandUtility.shipNode;
import static dev.wren.crowsnest.internal.Utility.convertToAABB;


public class ShipInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() { // mmm yes i must have every single value in LoadedShip
        return Commands.literal("ship")
            .then(
                Commands.argument("pos", BlockPosArgument.blockPos())
                    .then(shipNode("id", LoadedShip::getId, Long.class))
                    .then(shipNode("slug", LoadedShip::getSlug, String.class))
                    .then(shipNode("shipAABB", ship -> convertToAABB(ship.getShipAABB()), AABB.class))
                    .then(shipNode("worldAABB", ship -> convertToAABB(ship.getWorldAABB()), AABB.class))
                    .then(shipNode("shipToWorld", LoadedShip::getShipToWorld, Matrix4dc.class))
                    .then(shipNode("worldToShip", LoadedShip::getWorldToShip, Matrix4dc.class))
                    .then(branchNode("kinematics", LoadedShip::getKinematics, kBranch -> {
                        kBranch.commandNode("velocity", BodyKinematics::getVelocity).typeAdapter(Vector3dc.class);
                        kBranch.commandNode("rotation", BodyKinematics::getRotation).typeAdapter(Quaterniondc.class);
                        kBranch.commandNode("position", BodyKinematics::getPosition).typeAdapter(Vector3dc.class);
                        kBranch.commandNode("angularVelocity", BodyKinematics::getAngularVelocity).typeAdapter(Vector3dc.class);
                        kBranch.commandNode("scaling", BodyKinematics::getScaling).typeAdapter(Vector3dc.class);
                        kBranch.commandNode("worldToShip", BodyKinematics::getToModel).typeAdapter(Matrix4dc.class);
                        kBranch.commandNode("shipToWorld", BodyKinematics::getToWorld).typeAdapter(Matrix4dc.class);
                        kBranch.commandNode("transform", BodyKinematics::getTransform).typeAdapter(BodyTransform.class);
                    }))
            );
    }



}
