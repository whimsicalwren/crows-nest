package dev.wren.crowsnest.internal.operation;

import dev.wren.crowsnest.internal.pipeline.PipelineReader;

import java.util.function.BiFunction;

public class OperationDefinition<I, R> {

    private final Class<I> inputType;
    private final Class<R> returnType;
    private final BiFunction<I, ArgumentSet, R> operation;
    private final String name;
    private final ArgumentSet argumentSet;


    public OperationDefinition(Class<I> inputType, Class<R> returnType, BiFunction<I, ArgumentSet, R> operation, String name, ArgumentSet argumentSet) {
        this.inputType = inputType;
        this.returnType = returnType;
        this.operation = operation;
        this.name = name;
        this.argumentSet = argumentSet;
    }

    public Class<R> returnType() { return returnType; }
    public String name() { return name; }

    public void populateArgs(PipelineReader input) {
        this.argumentSet.fillValues(input);
    }

    public R perform(Object input) {
        I typedInput = inputType.cast(input);
        return operation.apply(typedInput, argumentSet);
    }
}