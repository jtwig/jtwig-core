package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;

import static org.jtwig.value.JtwigType.BOOLEAN;
import static org.jtwig.value.JtwigType.STRING;

public class BooleanAndStringLowerComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == BOOLEAN && (right.getType() == STRING)) {
            if (!left.asBoolean()) {
                if (right.asString().length() == 0) {
                    return Optional.of(false);
                } else {
                    return Optional.of(true);
                }
            }
        } else if (left.getType() == STRING && right.getType() == BOOLEAN) {
            if (right.asBoolean()) {
                if (left.asString().length() == 0) {
                    return Optional.of(true);
                } else {
                    return Optional.of(false);
                }
            }
            return Optional.of(false);
        }
        return Optional.absent();
    }
}
