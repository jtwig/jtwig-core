package org.jtwig.value.relational.equal;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;

public class NumberEqualComparator implements RelationalComparator {
    @Override
    public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
        Optional<BigDecimal> leftNumber = left.asNumber();
        Optional<BigDecimal> rightNumber = right.asNumber();
        if (leftNumber.isPresent() && rightNumber.isPresent()) {
            return Optional.of(leftNumber.get().equals(rightNumber.get()));
        }
        return Optional.absent();
    }
}
