package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.extract.Extractor;

public class StringTypeExtractor implements Extractor<JtwigType> {
    @Override
    public Optional<JtwigType> extract(Object value) {
        return CharSequence.class.isAssignableFrom(value.getClass()) || value instanceof Character ?
                Optional.of(JtwigType.STRING) : Optional.<JtwigType>absent();
    }
}
