package dev.wren.crowsnest.internal.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class ConverterRegistry {

    private static final Map<Class<?>, Function<?, ?>> CONVERTERS = new HashMap<>();
    private static final Map<Class<?>, Class<?>> RESULTS = new HashMap<>();

    public static <F, T> void registerConverter(Class<F> from, Class<T> to, Function<F, T> converter) {
        CONVERTERS.put(from, converter);
        RESULTS.put(from, to);
    }

    public static <F, T extends F> void registerCast(Class<F> from, Class<T> to) {
        registerConverter(from, to, to::cast);
    }

    @SuppressWarnings("unchecked")
    public static <F, T> Function<F, T> getConverter(Class<F> type) {
        for (Map.Entry<Class<?>, Function<?, ?>> entry : CONVERTERS.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                return (Function<F, T>) entry.getValue();
            }
        }
        return (Function<F, T>) CONVERTERS.getOrDefault(type, Function.identity());
    }

    @SuppressWarnings("unchecked")
    public static <F, T> Class<T> getResultClass(Class<F> type) {
        return (Class<T>) RESULTS.get(type);
    }


    public static <F, T> T convert(F from, Class<F> fClass) {
        Function<F, T> converter = getConverter(fClass);
        return converter.apply(from);
    }
}
