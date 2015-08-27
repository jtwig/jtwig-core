package org.jtwig.value.relational.identical;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

public class SameTypeComparator implements RelationalComparator {
    private final RelationalComparator relationalComparator;

    public SameTypeComparator(RelationalComparator relationalComparator) {
        this.relationalComparator = relationalComparator;
    }

    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        if (left.getType() == right.getType()) {
            return relationalComparator.apply(left, right);
        }
        return Optional.absent();
    }
}
