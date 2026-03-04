package dev.wren.crowsnest.internal;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.valkyrienskies.core.api.ships.LoadedShip;

import java.util.function.Consumer;
import java.util.function.Function;

import static dev.wren.crowsnest.internal.CommandUtility.shipHandle;

public class NestedBranchBuilder<T> {

    private final LiteralArgumentBuilder<CommandSourceStack> root;
    private final Function<LoadedShip, T> extractor;

    NestedBranchBuilder(LiteralArgumentBuilder<CommandSourceStack> root, Function<LoadedShip, T> extractor) {
        this.root = root;
        this.extractor = extractor;

        root.executes(ctx -> shipHandle(ctx, extractor, root.getLiteral()));
    }

    public <R> void add(String name, Function<T, R> func) {
        root.then(Commands.literal(name)
            .executes(ctx -> shipHandle(ctx, ship -> func.apply(extractor.apply(ship)), name))
        );
    }

    public static <T> LiteralArgumentBuilder<CommandSourceStack> nestedBranch(String name, Function<LoadedShip, T> extractor, Consumer<NestedBranchBuilder<T>> consumer) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(name);

        NestedBranchBuilder<T> builder = new NestedBranchBuilder<>(root, extractor);
        consumer.accept(builder);

        return root;
    }

    public <R> void subBranch(String name, Function<T, R> subExtractor, Consumer<NestedBranchBuilder<R>> consumer) {
        LiteralArgumentBuilder<CommandSourceStack> child =
                Commands.literal(name);

        root.then(child);

        NestedBranchBuilder<R> builder =
                new NestedBranchBuilder<>(
                        child,
                        ship -> subExtractor.apply(extractor.apply(ship))
                );

        consumer.accept(builder);
    }
}
