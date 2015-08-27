package org.jtwig.value.converter.string;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.TypeSpecificConverter;

public class StringConverter extends TypeSpecificConverter {
    public StringConverter(Function<Object, Optional<Value>> transform) {
        super(String.class, transform);
    }
}
