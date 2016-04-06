package org.jtwig.render.expression.calculator.enumerated.environment;

import org.jtwig.render.expression.calculator.enumerated.CompositeEnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;

import java.util.Collection;

public class EnumerationListStrategyFactory {
    public EnumerationListStrategy create (Collection<EnumerationListStrategy> listStrategies) {
        return new CompositeEnumerationListStrategy(listStrategies);
    }
}
