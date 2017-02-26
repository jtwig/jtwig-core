package org.jtwig.render;

import org.jtwig.environment.Environment;
import org.jtwig.render.context.RenderContext;

public class RenderRequest {
    private final RenderContext renderContext;
    private final Environment environment;

    public RenderRequest(RenderContext renderContext, Environment environment) {
        this.renderContext = renderContext;
        this.environment = environment;
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
