package org.jtwig.property.strategy.method.argument.group;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

public class SingleArgumentGroup implements ArgumentGroup {
    private final JavaMethodArgument javaMethodArgument;
    private final Object value;

    public SingleArgumentGroup(JavaMethodArgument javaMethodArgument, Object value) {
        this.javaMethodArgument = javaMethodArgument;
        this.value = value;
    }

    @Override
    public Optional<Value> toArgument(Converter converter) {
        return converter.convert(value, javaMethodArgument.type());
    }
}
