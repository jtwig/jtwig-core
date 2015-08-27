package org.jtwig.value.relational;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

import java.util.Collection;

public class CompositeRelationalComparator implements RelationalComparator {
    private final Collection<RelationalComparator> comparators;

    public CompositeRelationalComparator(Collection<RelationalComparator> comparators) {
        this.comparators = comparators;
    }

    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        for (RelationalComparator comparator : comparators) {
            Optional<Boolean> result = comparator.apply(left, right);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }
}
