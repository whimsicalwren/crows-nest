package dev.wren.crowsnest.internal.pipeline;

import dev.wren.crowsnest.internal.operation.OperationDefinition;

import java.util.List;

public record ImmutablePipeline(List<OperationDefinition<?, ?>> operations) {
    public ImmutablePipeline(Pipeline parent) {
        this(parent.getOperations());
    }
}
