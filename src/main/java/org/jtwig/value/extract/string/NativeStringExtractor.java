package org.jtwig.value.extract.string;

import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

public class NativeStringExtractor implements Extractor<String> {
    @Override
    public Optional<String> extract(Object value) {
        return Optional.of(value.toString());
    }
}
