package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

public class NullAndStringEqualComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == JtwigType.STRING && right.isNull()) {
            if (left.asString().length() == 0) {
                return Optional.of(true);
            } else {
                return Optional.of(false);
            }
        } else if (left.isNull() && right.getType() == JtwigType.STRING) {
            if (right.asString().length() == 0) {
                return Optional.of(true);
            } else {
                return Optional.of(false);
            }
        }
        return Optional.absent();
    }
}
