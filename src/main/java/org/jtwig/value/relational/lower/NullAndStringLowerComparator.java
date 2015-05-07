package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import static org.jtwig.value.JtwigType.BOOLEAN;
import static org.jtwig.value.JtwigType.STRING;

public class NullAndStringLowerComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.isNull() && (right.getType() == STRING)) {
            if (!left.asBoolean()) {
                if (right.asString().length() == 0) {
                    return Optional.of(false);
                } else {
                    return Optional.of(true);
                }
            }
        }
        return Optional.absent();
    }
}
