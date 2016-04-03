package org.jtwig.render.expression.calculator.enumerated.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.render.expression.calculator.enumerated.EnumerationListStrategy;

import java.util.ArrayList;
import java.util.Collection;

public class EnumerationListStrategyConfigurationBuilder<B extends EnumerationListStrategyConfigurationBuilder> implements Builder<EnumerationListStrategyConfiguration> {
    private final Collection<EnumerationListStrategy> enumerationListStrategies = new ArrayList<>();


    public EnumerationListStrategyConfigurationBuilder () {}
    public EnumerationListStrategyConfigurationBuilder(EnumerationListStrategyConfiguration prototype) {
        this.enumerationListStrategies.addAll(prototype.getStrategies());
    }

    public B withEnumerationListStrategy (EnumerationListStrategy enumerationListStrategy) {
        this.enumerationListStrategies.add(enumerationListStrategy);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public EnumerationListStrategyConfiguration build() {
        return new EnumerationListStrategyConfiguration(enumerationListStrategies);
    }
}
