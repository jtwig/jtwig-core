package org.jtwig.extension;

import org.jtwig.environment.EnvironmentConfigurationBuilder;

public interface Extension {
    void configure (EnvironmentConfigurationBuilder configurationBuilder);
}
