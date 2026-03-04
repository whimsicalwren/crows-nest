package dev.wren.crowsnest.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import dev.wren.crowsnest.internal.NestedBranchBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;

import org.valkyrienskies.core.api.bodies.properties.BodyKinematics;
import org.valkyrienskies.core.api.ships.LoadedShip;
import org.valkyrienskies.core.api.ships.properties.ShipTransform;

import static dev.wren.crowsnest.internal.CommandUtility.shipLiteral;
import static dev.wren.crowsnest.internal.Utility.*;

public class ShipInfoCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() { // mmm yes i must have every single value in LoadedShip
        return Commands.literal("ship")
            .then(
                Commands.argument("pos", BlockPosArgument.blockPos())
                    .then(shipLiteral("id", LoadedShip::getId))
                    .then(shipLiteral("slug", LoadedShip::getSlug))
                    .then(shipLiteral("shipAABB", ship -> convertToAABB(ship.getShipAABB())))
                    .then(shipLiteral("worldAABB", ship -> convertToAABB(ship.getWorldAABB())))
                    .then(shipLiteral("shipToWorld", LoadedShip::getShipToWorld))
                    .then(shipLiteral("worldToShip", LoadedShip::getWorldToShip))
                    .then(NestedBranchBuilder.nestedBranch("shipTransform", LoadedShip::getTransform, tBranch -> {
                        tBranch.add("positionInWorld", ShipTransform::getPositionInWorld);
                        tBranch.add("positionInShip", ShipTransform::getPositionInShip);
                        tBranch.add("shipToWorldScaling", ShipTransform::getShipToWorldScaling);
                        tBranch.add("shipToWorldRotation", ShipTransform::getShipToWorldRotation);
                    }))
                    .then(NestedBranchBuilder.nestedBranch("kinematics", LoadedShip::getKinematics, kBranch -> {
                        kBranch.add("position", BodyKinematics::getPosition);
                        kBranch.add("velocity", BodyKinematics::getVelocity);
                        kBranch.add("angularVelocity", BodyKinematics::getAngularVelocity);
                        kBranch.add("rotation", BodyKinematics::getRotation);
                        kBranch.add("scaling", BodyKinematics::getScaling);
                        kBranch.add("positionInModel", BodyKinematics::getPositionInModel);
                        kBranch.add("worldToShip", BodyKinematics::getToModel);
                        kBranch.add("shipToWorld", BodyKinematics::getToWorld);
                    }))
            );
    }



}
