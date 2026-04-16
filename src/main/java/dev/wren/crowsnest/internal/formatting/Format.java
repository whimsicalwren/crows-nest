package dev.wren.crowsnest.internal.formatting;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public record Format(Component component) {
    public static Format of(String content, ChatFormatting... formats) {
        return new Format(Component.literal(content).withStyle(formats));
    }

    public static Format of(Component content, ChatFormatting... formats) {
        return new Format(Component.literal(content.getString()).withStyle(formats));
    }

    public static Format of(Component base) {
        return new Format(base);
    }
}