package dev.wren.crowsnest.internal.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;

public class SkipArgumentType implements ArgumentType<Void> {
    @Override
    public Void parse(StringReader reader) {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof SkipArgumentType;
    }
}
