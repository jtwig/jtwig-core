package org.jtwig.value.extract.bool;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;

public class NullBooleanExtractor implements BooleanExtractor {
    @Override
    public Optional<Boolean> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of(false);
        }
        return Optional.absent();
    }
}
