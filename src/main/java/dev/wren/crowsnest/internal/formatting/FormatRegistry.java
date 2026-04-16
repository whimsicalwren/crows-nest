package dev.wren.crowsnest.internal.formatting;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dev.wren.crowsnest.internal.util.FormatUtil.NEWLINE;
import static dev.wren.crowsnest.internal.util.FormatUtil.formatSci;

public class FormatRegistry {

    private static final Map<Class<?>, Function<Object, Component>> FORMATTERS = new HashMap<>();

    public static <T> void registerFormat(Class<T> type, BiFunction<T, FormatBuilder, Component> formatter) {
        FORMATTERS.put(type, object -> formatter.apply(type.cast(object), new FormatBuilder()));
    }

    public static Component format(Object object, String commandName) {
        Component formatted = formatted(object);

        Component cmdName = Component.literal(commandName).withStyle(ChatFormatting.DARK_AQUA);

        Component name = Component.literal(object.getClass().getSimpleName()).withStyle(ChatFormatting.LIGHT_PURPLE);

        Component inter = Component.literal(" returned value of type: ").withStyle(ChatFormatting.WHITE);

        return Component.empty().append(cmdName).append(inter).append(name).append(NEWLINE.component()).append(formatted).withStyle(ChatFormatting.WHITE);
    }

    public static Component format(Object object) {
        Component formatted = formatted(object);

        Component name = Component.literal(object.getClass().getSimpleName()).withStyle(ChatFormatting.LIGHT_PURPLE);

        return Component.literal("Type: ").append(name).append(NEWLINE.component()).append(formatted);
    }

    private static Component formatted(Object object) {
        if (object == null) return Component.literal("null");

        Component formatted;

        Function<Object, Component> formatter = FORMATTERS.get(object.getClass());
        if (formatter != null)
            formatted = formatter.apply(object);
        else
            formatted = Component.literal(object.toString());

        return formatted;
    }
}
