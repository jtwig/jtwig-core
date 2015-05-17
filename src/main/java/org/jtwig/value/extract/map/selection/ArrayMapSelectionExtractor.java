package org.jtwig.value.extract.map.selection;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;

import java.math.BigDecimal;
import java.util.Map;

public class ArrayMapSelectionExtractor implements MapSelectionExtractor {
    @Override
    public Optional<Value> extract(Map map, JtwigValue key) {
        Optional<BigDecimal> number = key.asNumber();
        if (number.isPresent() && map.containsKey(number.get().intValue())) {
            return Optional.of(new Value(map.get(number.get().intValue())));
        }
        return Optional.absent();
    }
}
