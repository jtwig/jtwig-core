package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;

public class NumberStringEqualComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == JtwigType.NUMBER && right.getType() == JtwigType.STRING && !right.isStringNumber()) {
            return Optional.of(left.mandatoryNumber().equals(BigDecimal.ZERO));
        } else if (left.getType() == JtwigType.STRING) {
            if (!left.isStringNumber()) {
                if (right.getType() == JtwigType.NUMBER) {
                    return Optional.of(right.mandatoryNumber().equals(BigDecimal.ZERO));
                }
            }
        }
        return Optional.absent();
    }
}
