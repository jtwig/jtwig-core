package org.jtwig.value.extract.map;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.jtwig.value.extract.Extractor;

import java.util.Collections;
import java.util.Map;

public class NullMapExtractor implements Extractor<Map> {
    @Override
    public Optional<Map> extract(Object value) {
        if (value == null || value == Undefined.UNDEFINED) {
            return Optional.of((Map) Collections.emptyMap());
        }
        return Optional.absent();
    }
}
