package org.jtwig.model.expression.lists;

import java.util.Collection;

public class EnumerationListStrategyConfiguration {
    private final Collection<EnumerationListStrategy> strategies;

    public EnumerationListStrategyConfiguration(Collection<EnumerationListStrategy> strategies) {
        this.strategies = strategies;
    }

    public Collection<EnumerationListStrategy> getStrategies() {
        return strategies;
    }
}
