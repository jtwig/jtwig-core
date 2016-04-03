package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.render.expression.calculator.enumerated.config.EnumerationListStrategyConfiguration;
import org.jtwig.render.expression.calculator.enumerated.config.EnumerationListStrategyConfigurationBuilder;

public class AndEnumerationListStrategyConfigurationBuilder extends EnumerationListStrategyConfigurationBuilder<AndEnumerationListStrategyConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndEnumerationListStrategyConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super();
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndEnumerationListStrategyConfigurationBuilder(EnumerationListStrategyConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and () {
        return environmentConfigurationBuilder;
    }
}
