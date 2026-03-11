package dev.wren.crowsnest.internal.registries;

import dev.wren.crowsnest.internal.operation.OperationBuilder;
import dev.wren.crowsnest.internal.operation.OperationDefinition;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

import static dev.wren.crowsnest.CrowsNest.LOGGER;

public class OperationRegistry {

    private static final Map<Class<?>, Map<String, OperationDefinition<?, ?>>> operations = new HashMap<>();

    public static <T> void forType(Class<T> type, Consumer<OperationBuilder<T>> consumer) {
        OperationBuilder<T> builder = new OperationBuilder<>(type);
        consumer.accept(builder);

        Map<String, OperationDefinition<T, ?>> operationDefinitions = builder.opDefs;

        LOGGER.info("Registering {} operations for {}", operationDefinitions.size(), type.getCanonicalName());

        operations.computeIfAbsent(type, k -> new HashMap<>()).putAll(operationDefinitions);
    }

    public static @Nullable OperationDefinition<?, ?> getOperation(Class<?> type, String name) {
        return getOperations(type).stream().filter(opDef -> opDef.name().equals(name)).findFirst().orElse(null);
    }

    public static Collection<OperationDefinition<?, ?>> getOperations(Class<?> type) {
        List<OperationDefinition<?, ?>> result = new ArrayList<>();

        for (Map.Entry<Class<?>, Map<String, OperationDefinition<?, ?>>> entry : operations.entrySet()) {

            Class<?> registeredType = entry.getKey();

            if (registeredType.isAssignableFrom(type)) {
                result.addAll(entry.getValue().values());
            }
        }

        return result;
    }
}
