package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigType;

import java.util.ArrayList;
import java.util.Collection;

public class CharAscendingOrderEnumerationListStrategy implements EnumerationListStrategy {
    @Override
    public Optional<Collection<Object>> enumerate(JtwigValue left, JtwigValue right) {
        if (left.getType() == right.getType() && left.getType() == JtwigType.STRING) {
            Character start = left.asChar();
            Character end = right.asChar();
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
        return Optional.absent();
    }
}
