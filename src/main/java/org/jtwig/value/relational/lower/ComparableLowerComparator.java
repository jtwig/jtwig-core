package org.jtwig.value.relational.lower;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

public class ComparableLowerComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.asObject() instanceof Comparable && right.asObject() instanceof Comparable) {
            return Optional.of(((Comparable) left.asObject()).compareTo(right.asObject()) < 0);
        }
        return Optional.absent();
    }
}
