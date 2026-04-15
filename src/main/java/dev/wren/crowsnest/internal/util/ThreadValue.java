package dev.wren.crowsnest.internal.util;

public class ThreadValue {

    private static final ThreadLocal<Object> CURRENT = new ThreadLocal<>();

    public static void set(Object value) {
        CURRENT.set(value);
    }

    public static Object get() {
        return CURRENT.get();
    }

    public static <T> T getAs(Class<T> type) {
        return type.cast(get());
    }

    public static void clear() {
        CURRENT.remove();
    }
}
