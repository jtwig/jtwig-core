package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.render.config.RenderConfiguration;
import org.jtwig.render.config.RenderConfigurationBuilder;

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
