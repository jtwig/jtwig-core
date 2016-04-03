package org.jtwig.render.expression.calculator.enumerated.environment;

import org.jtwig.render.expression.calculator.enumerated.CompositeEnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.config.EnumerationListStrategyConfiguration;

public class EnumerationListStrategyFactory {
    public EnumerationListStrategy create (EnumerationListStrategyConfiguration configuration) {
        return new CompositeEnumerationListStrategy(configuration.getStrategies());
    }
}
