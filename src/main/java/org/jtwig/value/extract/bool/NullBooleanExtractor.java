package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.jtwig.value.extract.Extractor;

public class NullBooleanExtractor implements Extractor<Boolean> {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of(false);
        }
        return Optional.absent();
    }
}
