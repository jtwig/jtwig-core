package org.jtwig.render.expression.calculator.enumerated.config;

import org.jtwig.render.expression.calculator.enumerated.*;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class DefaultEnumerationListStrategyList extends ArrayList<EnumerationListStrategy> {
    public DefaultEnumerationListStrategyList() {
        super(asList(
                new IntegerAscendingOrderEnumerationListStrategy(),
                new IntegerDescendingOrderEnumerationListStrategy(),
                new CharAscendingOrderEnumerationListStrategy(),
                new CharDescendingOrderEnumerationListStrategy()
        ));
    }
}
