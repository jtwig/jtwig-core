package org.jtwig.value.extract.string;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.jtwig.value.extract.Extractor;

public class NullStringExtractor implements Extractor<String> {
    @Override
    public Optional<String> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of("");
        }
        return Optional.absent();
    }
}
