package org.jtwig.environment.and;

import org.jtwig.content.json.JsonMapperProviderConfiguration;
import org.jtwig.content.json.JsonMapperProviderConfigurationBuilder;
import org.jtwig.environment.EnvironmentConfigurationBuilder;

public class AndJsonMapperProviderConfigurationBuilder extends JsonMapperProviderConfigurationBuilder<AndJsonMapperProviderConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndJsonMapperProviderConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super();
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndJsonMapperProviderConfigurationBuilder(JsonMapperProviderConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and () {
        return environmentConfigurationBuilder;
    }
}
