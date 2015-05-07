package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;

import static org.jtwig.value.JtwigType.BOOLEAN;
import static org.jtwig.value.JtwigType.NUMBER;

public class NullAndNumberLowerComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if ((left.getType() == NUMBER || left.isStringNumber()) && right.isNull()) {
            return Optional.of(false);
        }
        if (left.isNull() && (right.getType() == NUMBER)) {
            if (right.mandatoryNumber().equals(BigDecimal.ZERO)) {
                return Optional.of(false);
            }
            return Optional.of(true);
        }
        return Optional.absent();
    }
}
