package org.jtwig.value.relational;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

public class NotComparator implements RelationalComparator {
    private final RelationalComparator relationalComparator;

    public NotComparator(RelationalComparator relationalComparator) {
        this.relationalComparator = relationalComparator;
    }

    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        return relationalComparator.apply(left, right).transform(
                new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean input) {
                        return !input;
                    }
                }
        );
    }
}
