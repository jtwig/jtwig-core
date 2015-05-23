package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.jtwig.value.JtwigType;
import org.jtwig.value.extract.Extractor;

public class AnyTypeExtractor implements Extractor<JtwigType> {
    @Override
    public Optional<JtwigType> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of(JtwigType.ANY);
        }
        return Optional.absent();
    }
}
