package org.jtwig.environment.and;

import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.escape.config.EscapeEngineConfiguration;
import org.jtwig.escape.config.EscapeEngineConfigurationBuilder;

public class AndEscapeEngineConfigurationBuilder extends EscapeEngineConfigurationBuilder<AndEscapeEngineConfigurationBuilder> implements AndBuilder<EnvironmentConfigurationBuilder> {
    private final EnvironmentConfigurationBuilder parent;

    public AndEscapeEngineConfigurationBuilder(EscapeEngineConfiguration prototype, EnvironmentConfigurationBuilder parent) {
        super(prototype);
        this.parent = parent;
    }

    public AndEscapeEngineConfigurationBuilder(EnvironmentConfigurationBuilder parent) {
        this.parent = parent;
    }

    @Override
    public EnvironmentConfigurationBuilder and() {
        return parent;
    }
}
