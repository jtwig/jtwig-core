package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.resource.config.ResourceConfigurationBuilder;

public class AndResourceConfigurationBuilder extends ResourceConfigurationBuilder<AndResourceConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder parent;

    public AndResourceConfigurationBuilder(EnvironmentConfigurationBuilder parent) {
        this.parent = parent;
    }

    public AndResourceConfigurationBuilder(ResourceConfiguration prototype, EnvironmentConfigurationBuilder parent) {
        super(prototype);
        this.parent = parent;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return parent;
    }
}
