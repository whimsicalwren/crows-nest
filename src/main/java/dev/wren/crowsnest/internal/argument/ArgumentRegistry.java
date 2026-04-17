package dev.wren.crowsnest.internal.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.wren.crowsnest.internal.formatting.ConverterRegistry;
import net.minecraft.commands.CommandSourceStack;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ArgumentRegistry {

    private static final Map<Class<?>, Supplier<? extends ArgumentType<?>>> ARGUMENT_TYPES = new HashMap<>();
    private static final Map<Class<? extends ArgumentType<?>>, BiFunction<CommandContext<CommandSourceStack>, String, ?>> ARGUMENT_GETTERS = new HashMap<>();

    public static <T, A extends ArgumentType<?>> void registerArgumentType(Class<T> type, Class<A> argTypeClass, Supplier<A> argTypeSupplier, BiFunction<CommandContext<CommandSourceStack>, String, T> getterFunc) {
        ARGUMENT_TYPES.put(type, argTypeSupplier);
        ARGUMENT_GETTERS.put(argTypeClass, getterFunc);
    }

    public static ArgumentType<?> getArgumentType(Class<?> type) {
        return ARGUMENT_TYPES.getOrDefault(type, SkipArgumentType::new).get();
    }

    @SuppressWarnings("unchecked")
    public static <T, A extends ArgumentType<?>> BiFunction<CommandContext<CommandSourceStack>, String, T> getGetter(Class<A> argTypeClass) {
        return (BiFunction<CommandContext<CommandSourceStack>, String, T>) ARGUMENT_GETTERS.get(argTypeClass);
    }

    public static ArgumentMap getArgumentTypes(Method method) {
        return ArgumentMap.parseFromMethod(method);
    }

}
