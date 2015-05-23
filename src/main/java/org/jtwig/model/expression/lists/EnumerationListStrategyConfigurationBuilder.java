package org.jtwig.model.expression.lists;

import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.Collection;

public class EnumerationListStrategyConfigurationBuilder implements Builder<EnumerationListStrategyConfiguration> {
    private final Collection<EnumerationListStrategy> enumerationListStrategies = new ArrayList<>();


    public EnumerationListStrategyConfigurationBuilder () {}
    public EnumerationListStrategyConfigurationBuilder(EnumerationListStrategyConfiguration prototype) {
        this.enumerationListStrategies.addAll(prototype.getStrategies());
    }

    @Override
    public EnumerationListStrategyConfiguration build() {
        return new EnumerationListStrategyConfiguration(enumerationListStrategies);
    }
}
