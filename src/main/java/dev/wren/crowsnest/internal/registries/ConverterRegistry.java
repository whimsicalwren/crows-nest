package dev.wren.crowsnest.internal.registries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static dev.wren.crowsnest.CrowsNest.LOGGER;

public class ConverterRegistry {

    private static final Map<Class<?>, Function<?, ?>> BRIDGES = new HashMap<>();

    public static <F, T> void registerBridge(Class<F> from, Class<T> to, Function<F, T> converter) {
        LOGGER.debug("Registering bridge from {} to {}", from.getCanonicalName(), to.getCanonicalName());

        BRIDGES.put(from, converter);
    }

    @SuppressWarnings("unchecked")
    public static <F, T> Function<F, T> getBridge(Class<F> type) {
        for (Map.Entry<Class<?>, Function<?, ?>> entry : BRIDGES.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                return (Function<F, T>) entry.getValue();
            }
        }
        return (Function<F, T>) BRIDGES.getOrDefault(type, Function.identity());
    }

    public static <F, T> T convert(F from, Class<F> fClass) {
        Function<F, T> bridge = getBridge(fClass);
        return bridge.apply(from);
    }
}
