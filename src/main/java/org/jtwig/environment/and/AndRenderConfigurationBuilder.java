package org.jtwig.environment.and;

import org.jtwig.context.RenderConfiguration;
import org.jtwig.context.RenderConfigurationBuilder;
import org.jtwig.environment.EnvironmentConfigurationBuilder;

public class AndRenderConfigurationBuilder extends RenderConfigurationBuilder<AndRenderConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder parent;

    public AndRenderConfigurationBuilder(EnvironmentConfigurationBuilder parent) {
        this.parent = parent;
    }

    public AndRenderConfigurationBuilder(RenderConfiguration prototype, EnvironmentConfigurationBuilder parent) {
        super(prototype);
        this.parent = parent;
    }

    @Override
    public EnvironmentConfigurationBuilder and () {
        return parent;
    }
}
