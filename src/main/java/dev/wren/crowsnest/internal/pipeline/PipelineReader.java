package dev.wren.crowsnest.internal.pipeline;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipelineReader {
    private List<String> content;

    public PipelineReader(String initial) {
        this.content = Arrays.stream(initial.split(" ")).toList();
    }

    public enum Values {
        DOUBLE,
        FLOAT,
        INT,
        VEC3,
        AXIS,
        STRING_OR_OTHER
    }

    public boolean canRead() {
        return !content.isEmpty();
    }

    private static PipelineReader.Values getValueForType(Class<?> type) {
        if (type == Vec3.class)
            return PipelineReader.Values.VEC3;
        else if (type == Integer.class)
            return PipelineReader.Values.INT;
        else if (type == Float.class)
            return PipelineReader.Values.FLOAT;
        else if (type == Double.class)
            return PipelineReader.Values.DOUBLE;
        else
            return PipelineReader.Values.STRING_OR_OTHER;
    }

    public Object getNext(Values type) {
        return switch (type) {
            case DOUBLE -> nextDouble();
            case FLOAT -> nextFloat();
            case VEC3 -> nextVec3();
            case INT -> nextInt();
            case AXIS -> nextAxis();
            case STRING_OR_OTHER -> nextString();
        };
    }

    public Object getNext(Class<?> type) {
        return getNext(getValueForType(type));
    }

    private String next() {
        return content.remove(0);
    }

    private String[] next(int amount) {
        List<String> result = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            result.add(content.remove(0));
        }
        return result.toArray(String[]::new);
    }

    public String nextString() {
        return next();
    }

    public Vec3 nextVec3() {
        return new Vec3(Double.parseDouble(next()), Double.parseDouble(next()), Double.parseDouble(next()));
    }

    public Double nextDouble() {
        return Double.parseDouble(next());
    }

    public Float nextFloat() {
        return Float.parseFloat(next());
    }

    public Integer nextInt() {
        return Integer.parseInt(next());
    }

    public Direction.Axis nextAxis() {
        return Direction.Axis.valueOf(next());
    }
}