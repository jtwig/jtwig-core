package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;

public class StringTypeExtractor implements TypeExtractor {
    @Override
    public Optional<JtwigType> extract(Object value) {
        return CharSequence.class.isAssignableFrom(value.getClass()) ?
                Optional.of(JtwigType.STRING) : Optional.<JtwigType>absent();
    }
}
