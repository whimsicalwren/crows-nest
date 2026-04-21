package dev.wren.crowsnest.internal.formatting;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

import static dev.wren.crowsnest.internal.util.FormatUtil.NEWLINE;
import static dev.wren.crowsnest.internal.util.FormatUtil.formatSci;

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

    public static Builder builder() {
        return new Builder(new ArrayList<>());
    }

    public static class Builder {
        private final List<Format> formatList;

        Builder(List<Format> initial) {
            this.formatList = initial;
        }

        public Builder format(Format... format) {
            formatList.addAll(List.of(format));
            return this;
        }

        public Builder newline() {
            return format(NEWLINE);
        }

        public Builder format(String content, ChatFormatting... formats) {
            formatList.add(Format.of(content, formats));
            return this;
        }

        public Builder format(double content, ChatFormatting... formats) {
            formatList.add(Format.of(formatSci(content), formats));
            return this;
        }

        public Builder format(Component base) {
            formatList.add(Format.of(base));
            return this;
        }

        public Component build() {
            MutableComponent initial = Component.literal("");
            for (Format format : this.formatList) {
                initial.append(format.component());
            }

            return initial;
        }
    }
}