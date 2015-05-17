package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

import java.util.Map;

public interface MapSelectionExtractor {
    Optional<Value> extract (Map map, JtwigValue key);
}
