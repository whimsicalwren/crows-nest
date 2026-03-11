package dev.wren.crowsnest.internal.operation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OperationBuilder<I> {

    private final Class<I> type;
    public final Map<String, OperationDefinition<I, ?>> opDefs = new HashMap<>();

    public OperationBuilder(Class<I> type) {
        this.type = type;
    }

    public <R> OperationBuilder<I> operation(Class<R> resultType, BiFunction<I, ArgumentSet, R> operation, String name, ArgumentSet argSet) {
        opDefs.put(name, new OperationDefinition<>(type, resultType, operation, name, argSet));
        return this;
    }

    public <R> OperationBuilder<I> noArg(Class<R> returnType, Function<I, R> operation, String name) {
        return operation(returnType, (input, ignored) -> operation.apply(input), name, ArgumentSet.empty());
    }
}