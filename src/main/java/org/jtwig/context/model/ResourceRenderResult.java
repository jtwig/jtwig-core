package org.jtwig.context.model;

import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;

public class ResourceRenderResult {
    private final Resource resource;
    private final ResourceContext context;
    private final Renderable renderable;

    public ResourceRenderResult(Resource resource, ResourceContext context, Renderable renderable) {
        this.resource = resource;
        this.context = context;
        this.renderable = renderable;
    }

    public Resource resource() {
        return resource;
    }

    public ResourceContext context() {
        return context;
    }

    public Renderable renderable() {
        return renderable;
    }
}
