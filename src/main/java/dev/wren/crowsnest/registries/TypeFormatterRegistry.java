package dev.wren.crowsnest.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TypeFormatterRegistry {

    private static final Map<Class<?>, Function<Object, String>> FORMATTERS = new HashMap<>();

    public static <T> void register(Class<T> type, Function<T, String> formatter) {
        FORMATTERS.put(type, object -> formatter.apply(type.cast(object)));
    }

    public static String format(Object object) {
        if (object == null) return "null";

        Class<?> objClass = object.getClass();

        Function<Object, String> formatter = FORMATTERS.get(objClass);
        if (formatter != null)
            return formatter.apply(object);

        return object.toString();
    }

}
