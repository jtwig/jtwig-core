package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.model.expression.lists.EnumerationListStrategyConfiguration;
import org.jtwig.model.expression.lists.EnumerationListStrategyConfigurationBuilder;

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
