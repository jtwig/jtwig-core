package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.jtwig.value.JtwigType;

public class AnyTypeExtractor implements TypeExtractor {
    @Override
    public Optional<JtwigType> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of(JtwigType.ANY);
        }
        return Optional.absent();
    }
}
