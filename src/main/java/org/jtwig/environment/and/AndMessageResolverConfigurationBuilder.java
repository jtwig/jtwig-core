package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.i18n.MessageResolverConfiguration;
import org.jtwig.i18n.MessageResolverConfigurationBuilder;

public class AndMessageResolverConfigurationBuilder extends MessageResolverConfigurationBuilder<AndMessageResolverConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder environmentConfigurationBuilder;

    public AndMessageResolverConfigurationBuilder(EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super();
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    public AndMessageResolverConfigurationBuilder(MessageResolverConfiguration prototype, EnvironmentConfigurationBuilder environmentConfigurationBuilder) {
        super(prototype);
        this.environmentConfigurationBuilder = environmentConfigurationBuilder;
    }

    @Override
    public EnvironmentConfigurationBuilder and () {
        return environmentConfigurationBuilder;
    }
}
