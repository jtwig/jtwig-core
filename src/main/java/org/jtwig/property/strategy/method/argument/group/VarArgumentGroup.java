package org.jtwig.property.strategy.method.argument.group;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

import java.lang.reflect.Array;
import java.util.List;

public class VarArgumentGroup implements ArgumentGroup {
    private final JavaMethodArgument javaMethodArgument;
    private final List<Object> arguments;

    public VarArgumentGroup(JavaMethodArgument javaMethodArgument, List<Object> arguments) {
        this.javaMethodArgument = javaMethodArgument;
        this.arguments = arguments;
    }

    @Override
    public Optional<Value> toArgument(Converter converter) {
        Class componentType = javaMethodArgument.type().getComponentType();

        Object[] array = (Object[]) Array.newInstance(componentType, arguments.size());
        for (int i = 0; i < arguments.size(); i++) {
            Optional<Value> convert = converter.convert(arguments.get(i), componentType);
            if (!convert.isPresent()) return Optional.absent();
            array[i] = convert.get().getValue();
        }

        return Optional.of(new Value(array));
    }
}
