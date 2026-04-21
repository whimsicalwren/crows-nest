package dev.wren.crowsnest.internal.util;

import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;

public class ValueSource implements CommandSource {

    private final Object held;
    private final CommandSource from;

    public Object getHeld() {
        return held;
    }

    public <T> T getAs(Class<T> type) {
        return type.cast(held);
    }

    public ValueSource(Object held, CommandSource from) {
        this.held = held;
        this.from = from;
    }

    public ValueSource withResult(Object result) {
        return new ValueSource(result, this.from);
    }

    @Override
    public void sendSystemMessage(Component component) {
        this.from.sendSystemMessage(component);
    }

    @Override
    public boolean acceptsSuccess() {
        return this.from.acceptsSuccess();
    }

    @Override
    public boolean acceptsFailure() {
        return this.from.acceptsFailure();
    }

    @Override
    public boolean shouldInformAdmins() {
        return this.from.shouldInformAdmins();
    }

    @Override
    public boolean alwaysAccepts() {
        return this.from.alwaysAccepts();
    }
}
