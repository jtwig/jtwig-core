package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.value.config.ValueConfiguration;
import org.jtwig.value.config.ValueConfigurationBuilder;

public class AndValueConfigurationBuilder extends ValueConfigurationBuilder<AndValueConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndValueConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndValueConfigurationBuilder(ValueConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return environmentConfigurationBuilder;
    }
}
