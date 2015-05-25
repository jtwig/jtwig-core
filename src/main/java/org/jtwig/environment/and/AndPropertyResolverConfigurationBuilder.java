package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.property.PropertyResolverConfiguration;
import org.jtwig.property.PropertyResolverConfigurationBuilder;

public class AndPropertyResolverConfigurationBuilder extends PropertyResolverConfigurationBuilder<AndPropertyResolverConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndPropertyResolverConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndPropertyResolverConfigurationBuilder(PropertyResolverConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return environmentConfigurationBuilder;
    }
}
