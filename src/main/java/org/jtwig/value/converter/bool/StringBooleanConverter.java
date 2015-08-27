package org.jtwig.value.converter.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

public class StringBooleanConverter extends BooleanConverter {
    public StringBooleanConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value instanceof CharSequence) {
                    return Optional.of(new Value(((CharSequence) value).length() > 0));
                }
                return Optional.absent();
            }
        });
    }
}
