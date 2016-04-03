package org.jtwig.render.expression.calculator.enumerated;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;

import java.util.Collection;
import java.util.List;

public class CompositeEnumerationListStrategy implements EnumerationListStrategy {
    private final Collection<EnumerationListStrategy> strategies;

    public CompositeEnumerationListStrategy(Collection<EnumerationListStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Optional<List<Object>> enumerate(RenderRequest request, Object left, Object right) {
        for (EnumerationListStrategy strategy : strategies) {
            Optional<List<Object>> result = strategy.enumerate(request, left, right);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }
}
