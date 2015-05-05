package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

import java.util.Collection;

public class CompositeEnumerationListStrategy implements EnumerationListStrategy {
    private final Collection<EnumerationListStrategy> strategies;

    public CompositeEnumerationListStrategy(Collection<EnumerationListStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Optional<Collection<Object>> enumerate(JtwigValue left, JtwigValue right) {
        for (EnumerationListStrategy strategy : strategies) {
            Optional<Collection<Object>> result = strategy.enumerate(left, right);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }
}
