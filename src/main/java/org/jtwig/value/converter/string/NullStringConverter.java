package org.jtwig.value.converter.string;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.Undefined;

public class NullStringConverter extends StringConverter {
    public NullStringConverter() {
        super(new Function<Object, Optional<Value>>() {
            @Override
            public Optional<Value> apply(Object value) {
                if (value == null || value == Undefined.UNDEFINED) {
                    return Optional.of(new Value(""));
                }
                return Optional.absent();
            }
        });
    }
}
