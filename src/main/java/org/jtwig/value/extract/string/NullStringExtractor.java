package org.jtwig.value.extract.string;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;

public class NullStringExtractor implements StringExtractor {
    @Override
    public Optional<String> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of("");
        }
        return Optional.absent();
    }
}
