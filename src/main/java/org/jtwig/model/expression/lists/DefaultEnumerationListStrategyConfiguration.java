package org.jtwig.model.expression.lists;

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
