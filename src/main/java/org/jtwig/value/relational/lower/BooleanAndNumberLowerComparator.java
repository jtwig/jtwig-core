package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;

import static org.jtwig.value.JtwigType.BOOLEAN;
import static org.jtwig.value.JtwigType.NUMBER;

public class BooleanAndNumberLowerComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == BOOLEAN && (right.getType() == NUMBER || right.isStringNumber())) {
            if (left.asBoolean()) {
                return Optional.of(false);
            } else {
                if (right.mandatoryNumber().equals(BigDecimal.ZERO)) {
                    return Optional.of(false);
                }
                return Optional.of(true);
            }
        } else if ((left.getType() == NUMBER || left.isStringNumber()) && right.getType() == BOOLEAN) {
            if (left.mandatoryNumber().equals(BigDecimal.ZERO)) {
                return Optional.of(right.asBoolean());
            }
            return Optional.of(false);
        }
        return Optional.absent();
    }
}
