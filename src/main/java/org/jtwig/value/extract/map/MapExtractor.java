package org.jtwig.value.extract.map;

import com.google.common.base.Optional;

import java.util.Map;

public interface MapExtractor {
    Optional<Map> extract (Object value);
}
