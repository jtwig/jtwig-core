package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.value.configuration.ValueConfiguration;
import org.jtwig.value.configuration.ValueConfigurationBuilder;

public class AndValueConfigurationBuilder extends ValueConfigurationBuilder<AndValueConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder parent;

    public AndValueConfigurationBuilder(EnvironmentConfigurationBuilder parent) {
        this.parent = parent;
    }

    public AndValueConfigurationBuilder(ValueConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.parent = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return parent;
    }
}
