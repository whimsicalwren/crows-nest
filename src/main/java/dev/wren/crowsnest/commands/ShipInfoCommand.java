package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import dev.wren.crowsnest.internal.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;

import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.bodies.properties.BodyKinematics;
import org.valkyrienskies.core.api.ships.LoadedShip;

import static dev.wren.crowsnest.internal.CommandUtility.shipNode;


public class ShipInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() { // mmm yes i must have every single value in LoadedShip
//        return Commands.literal("ship")
//            .then(
//                Commands.argument("pos", BlockPosArgument.blockPos())
//                    .then(shipLiteral("id", LoadedShip::getId, Long.class))
//                    .then(shipLiteral("slug", LoadedShip::getSlug, String.class))
//                    .then(shipLiteral("shipAABB", ship -> convertToAABB(ship.getShipAABB()), AABB.class))
//                    .then(shipLiteral("worldAABB", ship -> convertToAABB(ship.getWorldAABB()), AABB.class))
//                    .then(shipLiteral("shipToWorld", LoadedShip::getShipToWorld, Matrix4dc.class))
//                    .then(shipLiteral("worldToShip", LoadedShip::getWorldToShip, Matrix4dc.class))
//                    .then(NestedBranchBuilder.nestedBranch("kinematics", LoadedShip::getKinematics, BodyKinematics.class, kBranch -> {
//                        kBranch.add("position", BodyKinematics::getPosition, Vector3dc.class);
//                        kBranch.add("velocity", BodyKinematics::getVelocity, Vector3dc.class);
//                        kBranch.add("angularVelocity", BodyKinematics::getAngularVelocity, Vector3dc.class);
//                        kBranch.add("rotation", BodyKinematics::getRotation, Quaterniondc.class);
//                        kBranch.add("scaling", BodyKinematics::getScaling, Vector3dc.class);
//                        kBranch.add("positionInModel", BodyKinematics::getPositionInModel, Vector3dc.class);
//                        kBranch.add("worldToShip", BodyKinematics::getToModel, Matrix4dc.class);
//                        kBranch.add("shipToWorld", BodyKinematics::getToWorld, Matrix4dc.class);
//                    }))
//            );
        return Commands.literal("ship")
                .then(
                        Commands.argument("pos", BlockPosArgument.blockPos())
                                .then(shipNode("id", LoadedShip::getId, Long.class))
                                .then(new CommandNode<>("slug", LoadedShip::getSlug).typeAdapter(String.class).build())
                                .then(new CommandNode<>("kinematics", LoadedShip::getKinematics).typeAdapter(BodyKinematics.class).subCommands(kBranch -> {
                                    kBranch.commandNode("velocity", BodyKinematics::getVelocity).typeAdapter(Vector3dc.class);
                                    kBranch.commandNode("rotation", BodyKinematics::getRotation).typeAdapter(Quaterniondc.class);
                                }).build())
                );
    }



}
