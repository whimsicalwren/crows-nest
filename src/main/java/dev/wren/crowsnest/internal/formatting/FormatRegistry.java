package dev.wren.crowsnest.internal.formatting;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static dev.wren.crowsnest.internal.util.FormatUtil.NEWLINE;
import static dev.wren.crowsnest.internal.util.FormatUtil.formatSci;

public class FormatRegistry {

    private static final Map<Class<?>, Function<Object, Component>> FORMATTERS = new HashMap<>();

    public static <T> void registerFormat(Class<T> type, BiFunction<T, Format.Builder, Component> formatter) {
        FORMATTERS.put(type, object -> formatter.apply(type.cast(object), Format.builder()));
    }

    public static Component format(Object object, String commandName) {
        Component formatted = formatRaw(object);

        Component cmdName = Component.literal(commandName).withStyle(ChatFormatting.DARK_AQUA);

        Component name = Component.literal(object.getClass().getSimpleName()).withStyle(ChatFormatting.LIGHT_PURPLE);

        Component inter = Component.literal(" returned value of type: ").withStyle(ChatFormatting.WHITE);

        return Component.empty().append(cmdName).append(inter).append(name).append(NEWLINE.component()).append(formatted).withStyle(ChatFormatting.WHITE);
    }

    public static Component format(Object object) {
        Component formatted = formatRaw(object);

        Component name = Component.literal(object.getClass().getSimpleName()).withStyle(ChatFormatting.LIGHT_PURPLE);

        return Component.literal("Type: ").append(name).append(NEWLINE.component()).append(formatted);
    }

    @SuppressWarnings("unchecked")
    public static String formatClassName(Class<?> c) {
        StringBuilder name = new StringBuilder(c.getSimpleName());

        if (c.getTypeParameters().length > 0) {
            name.append("<");
            Iterator<? extends TypeVariable<? extends Class<?>>> itr = Arrays.stream(c.getTypeParameters()).iterator();

            TypeVariable<Class<?>> next = (TypeVariable<Class<?>>) itr.next();

            name.append(next.getName());

            while (itr.hasNext()) {
                TypeVariable<Class<?>> next1 = (TypeVariable<Class<?>>) itr.next();

                name.append(", ").append(next1.getName());
            }

            name.append(">");
        }

        return name.toString();
    }

    public static Component formatRaw(Object object) {
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
