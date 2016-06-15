package org.jtwig.property.method.argument;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethodArgument;

public class ArgumentConverter {
    private final IsNativeType isNativeType;
    private final AssignableTypes assignableTypes;

    public ArgumentConverter(IsNativeType isNativeType, AssignableTypes assignableTypes) {
        this.isNativeType = isNativeType;
        this.assignableTypes = assignableTypes;
    }

    public Optional<Value> convert(JavaMethodArgument argument, Object next) {
        if ((next != null)) {
            if (assignableTypes.isAssignable(argument.type(), next.getClass())) {
                return Optional.of(new Value(next));
            } else {
                return Optional.absent();
            }
        } else {
            if (isNativeType.isNative(argument.type())) {
                return Optional.absent();
            } else {
                return Optional.of(new Value(null));
            }
        }
    }
}
