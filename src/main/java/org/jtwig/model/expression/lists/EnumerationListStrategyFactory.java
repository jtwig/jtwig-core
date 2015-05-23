package org.jtwig.model.expression.lists;

public class EnumerationListStrategyFactory {
    public EnumerationListStrategy create (EnumerationListStrategyConfiguration configuration) {
        return new CompositeEnumerationListStrategy(configuration.getStrategies());
    }
}
