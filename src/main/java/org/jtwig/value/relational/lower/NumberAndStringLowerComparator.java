package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;

import static org.jtwig.value.JtwigType.NUMBER;
import static org.jtwig.value.JtwigType.STRING;

public class NumberAndStringLowerComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == NUMBER && (right.getType() == STRING && !right.isStringNumber())) {
            return Optional.of(left.mandatoryNumber().compareTo(BigDecimal.ZERO) < 0);
        } else if ((left.getType() == STRING && !left.isStringNumber()) && right.getType() == NUMBER) {
            return Optional.of(right.mandatoryNumber().compareTo(BigDecimal.ZERO) > 0);
        }
        return Optional.absent();
    }
}
