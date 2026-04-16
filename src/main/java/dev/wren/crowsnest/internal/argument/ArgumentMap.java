package dev.wren.crowsnest.internal.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class ArgumentMap extends LinkedHashMap<String, ArgumentType<?>> {

    public boolean isValid() {
        return !this.containsValue(new SkipArgumentType());
    }

    public static ArgumentMap empty() {
        return new ArgumentMap();
    }

    static ArgumentMap parseFromMethod(Method method) {
        List<Parameter> params = Arrays.stream(method.getParameters()).toList();
        List<? extends ArgumentType<?>> argTypes = params.stream().map(param -> ArgumentRegistry.getArgumentType(param.getType())).toList();
        List<String> argNames = params.stream().map(Parameter::getName).toList();

        ArgumentMap argumentMap = new ArgumentMap();

        Iterator<? extends ArgumentType<?>> typeItr = argTypes.iterator();
        Iterator<String> nameItr = argNames.iterator();

        while (nameItr.hasNext() && typeItr.hasNext()) {
            argumentMap.put(nameItr.next(), typeItr.next());
        }

        return argumentMap;
    }

    public Object[] getArguments(CommandContext<CommandSourceStack> ctx) {
        List<Object> values = new ArrayList<>();

        for (Map.Entry<String, ArgumentType<?>> argEntry : this.entrySet()) {
            String name = argEntry.getKey();
            ArgumentType<?> argType = argEntry.getValue();
            values.add(ArgumentRegistry.getGetter(argType.getClass()).apply(ctx, name));
        }

        return values.toArray(Object[]::new);
    }

}
