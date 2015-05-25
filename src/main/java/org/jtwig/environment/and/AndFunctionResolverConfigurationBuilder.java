package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.functions.resolver.FunctionResolverConfiguration;
import org.jtwig.functions.resolver.FunctionResolverConfigurationBuilder;

public class AndFunctionResolverConfigurationBuilder extends FunctionResolverConfigurationBuilder<AndFunctionResolverConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder parent;

    public AndFunctionResolverConfigurationBuilder(EnvironmentConfigurationBuilder parent) {
        this.parent = parent;
    }

    public AndFunctionResolverConfigurationBuilder(FunctionResolverConfiguration prototype, EnvironmentConfigurationBuilder parent) {
        super(prototype);
        this.parent = parent;
    }

    @Override
    public EnvironmentConfigurationBuilder and () {
        return parent;
    }
}
