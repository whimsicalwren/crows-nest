package dev.wren.crowsnest.internal.registries;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.valkyrienskies.core.impl.shadow.An;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dev.wren.crowsnest.internal.util.FormatUtil.formatSci;

public class FormatRegistry {

    private static final Map<Class<?>, Function<Object, Component>> FORMATTERS = new HashMap<>();

    public static <T> void registerFormat(Class<T> type, BiFunction<T, FormatBuilder, Component> formatter) {
        FORMATTERS.put(type, object -> formatter.apply(type.cast(object), new FormatBuilder()));
    }

    public static Component format(Object object, String commandName) {
        if (object == null) return Component.literal("null");

        Component formatted;

        Function<Object, Component> formatter = FORMATTERS.get(object.getClass());
        if (formatter != null)
            formatted = formatter.apply(object);
        else
            formatted = Component.literal(object.toString());

        return Component.literal(commandName + " returned value of type: " + object.getClass().getSimpleName() + "\n").append(formatted);
    }

    public static Component format(Object object) {
        if (object == null) return Component.literal("null");

        Component formatted;

        Function<Object, Component> formatter = FORMATTERS.get(object.getClass());
        if (formatter != null)
            formatted = formatter.apply(object);
        else
            formatted = Component.literal(object.toString());

        return Component.literal("Type: " + object.getClass().getSimpleName() + "\n").append(formatted);
    }


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

    public static class FormatBuilder {
        private final ArrayList<Format> formatList;

        public FormatBuilder() {
            formatList = new ArrayList<>();
        }

        public FormatBuilder format(Format... format) {
            formatList.addAll(List.of(format));
            return this;
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
}
