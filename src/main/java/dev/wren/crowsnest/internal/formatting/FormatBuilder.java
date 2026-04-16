package dev.wren.crowsnest.internal.formatting;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

import static dev.wren.crowsnest.internal.util.FormatUtil.NEWLINE;
import static dev.wren.crowsnest.internal.util.FormatUtil.formatSci;

public class FormatBuilder {
    private final ArrayList<Format> formatList;

    public FormatBuilder() {
        formatList = new ArrayList<>();
    }

    public FormatBuilder format(Format... format) {
        formatList.addAll(List.of(format));
        return this;
    }

    public FormatBuilder newline() {
        return format(NEWLINE);
    }

    public FormatBuilder format(String content, ChatFormatting... formats) {
        formatList.add(Format.of(content, formats));
        return this;
    }

    public FormatBuilder format(double content, ChatFormatting... formats) {
        formatList.add(Format.of(formatSci(content), formats));
        return this;
    }

    public FormatBuilder format(Component base) {
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