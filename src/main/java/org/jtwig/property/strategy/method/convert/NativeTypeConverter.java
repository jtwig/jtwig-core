package org.jtwig.property.strategy.method.convert;

import com.google.common.base.Optional;
import org.jtwig.property.strategy.method.argument.AssignableTypes;
import org.jtwig.property.strategy.method.argument.IsNativeType;
import org.jtwig.reflection.model.Value;

public class NativeTypeConverter implements Converter {
    private final IsNativeType isNativeType;
    private final AssignableTypes assignableTypes;

    public NativeTypeConverter(IsNativeType isNativeType, AssignableTypes assignableTypes) {
        this.isNativeType = isNativeType;
        this.assignableTypes = assignableTypes;
    }

    @Override
    public Optional<Value> convert(Object value, Class type) {
        if ((value != null)) {
            if (assignableTypes.isAssignable(type, value.getClass())) {
                return Optional.of(new Value(value));
            } else {
                return Optional.absent();
            }
        } else {
            if (isNativeType.isNative(type)) {
                return Optional.absent();
            } else {
                return Optional.of(new Value(null));
            }
        }
    }
}
