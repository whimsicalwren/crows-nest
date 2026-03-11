package dev.wren.crowsnest.internal.operation;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

import dev.wren.crowsnest.internal.pipeline.PipelineReader;

import java.util.HashMap;
import java.util.Map;

public class ArgumentSet {

    private final Map<String, Object> values;
    private final Map<String, Class<?>> args;

    public ArgumentSet(Map<String, Class<?>> args) {
        this.args = args;
        this.values = new HashMap<>();
    }

    public ArgumentSet() {
        this.args = new HashMap<>();
        this.values = new HashMap<>();
    }

    public Float getFloat(String key) {
        return (Float) getOrThrow(key);
    }

    public Double getDouble(String key) {
        return (Double) getOrThrow(key);
    }

    public Vec3 getVec3(String key) {
        return (Vec3) getOrThrow(key);
    }

    public Integer getInt(String key) {
        return (Integer) getOrThrow(key);
    }

    public String get(String key) {
        return getOrThrow(key).toString();
    }

    public Direction.Axis getAxis(String key) {
        return Direction.Axis.valueOf(get(key));
    }


    private Object getOrThrow(String key) {
        Object value = values.get(key);
        if (value == null) throw new IllegalArgumentException("Argument " + key + " not found!");
        return value;
    }

    public void fillValues(PipelineReader input) {
        for (Map.Entry<String, Class<?>> entry : args.entrySet()) {
            values.put(entry.getKey(), input.getNext(entry.getValue()));
        }
    }

    public static ArgumentSet empty() {
        return new ArgumentSet();
    }

    public static ArgumentSet vec3(String key) {
        return of(Map.of(key, Vec3.class));
    }

    public static ArgumentSet d(String key) {
        return of(Map.of(key, Double.class));
    }

    public static ArgumentSet f(String key) {
        return of(Map.of(key, Float.class));
    }

    public static ArgumentSet i(String key) {
            return of(Map.of(key, Integer.class));
    }

    public static ArgumentSet s(String key) {
        return of(Map.of(key, String.class));
    }

    public static ArgumentSet axis(String key) {
        return of(Map.of(key, Direction.Axis.class));
    }

    public static ArgumentSet of(Map<String, Class<?>> args) {
        return new ArgumentSet(args);
    }
}