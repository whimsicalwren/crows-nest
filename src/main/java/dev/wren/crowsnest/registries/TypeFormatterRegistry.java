package dev.wren.crowsnest.registries;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.checkerframework.checker.units.qual.C;
import org.valkyrienskies.core.impl.shadow.Co;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TypeFormatterRegistry {

    private static final Map<Class<?>, Function<Object, Component>> FORMATTERS = new HashMap<>();

    public static <T> void registerFormatter(Class<T> type, Function<T, Component> formatter) {
        FORMATTERS.put(type, object -> formatter.apply(type.cast(object)));
    }

    public static Component format(Object object) {
        if (object == null) return Component.literal("null");

        Class<?> objClass = object.getClass();

        Function<Object, Component> formatter = FORMATTERS.get(objClass);
        if (formatter != null)
            return formatter.apply(object);

        return Component.literal(object.toString());
    }
}
