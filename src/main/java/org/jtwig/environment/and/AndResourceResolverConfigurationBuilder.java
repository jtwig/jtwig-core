package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.resource.resolver.ResourceResolverConfiguration;
import org.jtwig.resource.resolver.ResourceResolverConfigurationBuilder;

public class AndResourceResolverConfigurationBuilder extends ResourceResolverConfigurationBuilder<AndResourceResolverConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndResourceResolverConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndResourceResolverConfigurationBuilder(ResourceResolverConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return environmentConfigurationBuilder;
    }
}
