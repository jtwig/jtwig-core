package org.jtwig.escape.environment;

import com.google.common.base.Preconditions;
import org.jtwig.escape.EscapeEngineSelector;
import org.jtwig.escape.config.EscapeEngineConfiguration;

public class EscapeEnvironmentFactory {
    public EscapeEnvironment create (EscapeEngineConfiguration configuration) {
        Preconditions.checkArgument(configuration.getEscapeEngineMap().containsKey(configuration.getInitialEngine()), String.format("Invalid initial escape mode %s, it must be one of %s", configuration.getInitialEngine(), configuration.getEscapeEngineMap().keySet()));
        Preconditions.checkArgument(configuration.getEscapeEngineMap().containsKey(configuration.getDefaultEngine()), String.format("Invalid default escape mode %s, it must be one of %s", configuration.getDefaultEngine(), configuration.getEscapeEngineMap().keySet()));

        return new EscapeEnvironment(
                configuration.getEscapeEngineMap().get(configuration.getInitialEngine()),
                configuration.getDefaultEngine(),
                EscapeEngineSelector.newInstance(configuration.getEscapeEngineMap())
        );
    }
}
