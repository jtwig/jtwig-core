package org.jtwig.value.extract.string;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

public class BooleanStringExtractor implements Extractor<String> {

    @Override
    public Optional<String> extract(Object value) {
        if (value instanceof Boolean) {
            return Optional.of((Boolean) value ? "1" : "");
        }
        return Optional.absent();
    }
}
