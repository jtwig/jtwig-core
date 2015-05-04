package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.util.JtwigValue;

import java.util.ArrayList;
import java.util.Collection;

public class IntegerAscendingOrderEnumerationListStrategy implements EnumerationListStrategy {
    @Override
    public Optional<Collection<Object>> enumerate(JtwigValue left, JtwigValue right) {
        if (left.getType() == right.getType()) {
            if (left.getType() == JtwigValue.Type.NUMBER) {
                int start = left.mandatoryNumber().intValue();
                int end = right.mandatoryNumber().intValue();
                if (start <= end) {
                    Collection<Object> result = new ArrayList<>();
                    while (start < end) {
                        result.add(start);
                        start++;
                    }
                    result.add(start);
                    return Optional.of(result);
                }
            }
        }
        return Optional.absent();
    }
}
