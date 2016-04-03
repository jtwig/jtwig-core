package org.jtwig.render.expression.calculator.enumerated.config;

import org.jtwig.render.expression.calculator.enumerated.CharAscendingOrderEnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.CharDescendingOrderEnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.IntegerAscendingOrderEnumerationListStrategy;
import org.jtwig.render.expression.calculator.enumerated.IntegerDescendingOrderEnumerationListStrategy;

import static java.util.Arrays.asList;

public class DefaultEnumerationListStrategyConfiguration extends EnumerationListStrategyConfiguration {
    public DefaultEnumerationListStrategyConfiguration() {
        super(asList(
                new IntegerAscendingOrderEnumerationListStrategy(),
                new IntegerDescendingOrderEnumerationListStrategy(),
                new CharAscendingOrderEnumerationListStrategy(),
                new CharDescendingOrderEnumerationListStrategy()
        ));
    }
}
