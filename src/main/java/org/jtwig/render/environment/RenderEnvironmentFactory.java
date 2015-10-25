package org.jtwig.render.environment;

import org.jtwig.render.config.RenderConfiguration;

public class RenderEnvironmentFactory {
    public RenderEnvironment create (RenderConfiguration renderConfiguration) {
        return new RenderEnvironment(renderConfiguration.getStrictMode(), renderConfiguration.getDefaultOutputCharset(), renderConfiguration.getInitialEscapeMode());
    }
}
