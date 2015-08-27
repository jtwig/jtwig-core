package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

public class StringEqualComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == JtwigType.STRING && right.getType() == JtwigType.STRING) {
            return Optional.of(left.asString().equals(right.asString()));
        }
        return Optional.absent();
    }
}
