package dev.wren.crowsnest.internal;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.wren.crowsnest.internal.reg.TypeBranchRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.valkyrienskies.core.api.ships.LoadedShip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static dev.wren.crowsnest.internal.CommandUtility.shipHandle;

public class CommandNode<T> {

    private final String name;
    private final Function<LoadedShip, T> extractor;
    private boolean built = false;
    private LiteralArgumentBuilder<CommandSourceStack> cached;

    private final List<CommandNode<?>> children = new ArrayList<>();

    private Class<T> adapterType;

    public CommandNode(String name, Function<LoadedShip, T> extractor) {
        this.name = name;
        this.extractor = extractor;
    }

    public CommandNode<T> typeAdapter(Class<T> type) {
        this.adapterType = type;
        return this;
    }

    public CommandNode<T> subCommands(Consumer<CommandNode<T>> consumer) {
        consumer.accept(this);
        return this;
    }

    public <R> CommandNode<R> commandNode(String name, Function<T, R> extractor) {

        CommandNode<R> child = new CommandNode<>(name, ship -> extractor.apply(this.extractor.apply(ship)));

        children.add(child);

        return child;
    }

    public LiteralArgumentBuilder<CommandSourceStack> build() {

        if (built) return cached;

        LiteralArgumentBuilder<CommandSourceStack> node = Commands.literal(name);

        if (adapterType != null) {
            TypeBranchRegistry.applyIfPresent(adapterType, this);
        }

        node.executes(ctx -> shipHandle(ctx, extractor, name));

        for (CommandNode<?> child : children) {
            node.then(child.build());
        }

        cached = node;
        built = true;

        return node;
    }
}