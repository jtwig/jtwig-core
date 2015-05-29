package org.jtwig.addon;

import org.jtwig.environment.EnvironmentConfigurationBuilder;

public interface AddonProvider {
    void configure (EnvironmentConfigurationBuilder configurationBuilder);
}
