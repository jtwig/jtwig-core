package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

public class BooleanEqualComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == JtwigType.BOOLEAN || right.getType() == JtwigType.BOOLEAN) {
            return Optional.of(left.asBoolean() == right.asBoolean());
        }
        return Optional.absent();
    }
}
