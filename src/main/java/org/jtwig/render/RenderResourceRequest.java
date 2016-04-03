package org.jtwig.render;

import org.jtwig.resource.Resource;
import org.jtwig.value.WrappedCollection;

public class RenderResourceRequest {
    private final Resource resource;
    private final boolean newBlockContext;
    private final boolean newValueContext;
    private final WrappedCollection includeModel;

    public RenderResourceRequest(Resource resource, boolean newBlockContext, boolean newValueContext, WrappedCollection includeModel) {
        this.resource = resource;
        this.newBlockContext = newBlockContext;
        this.newValueContext = newValueContext;
        this.includeModel = includeModel;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean isNewBlockContext() {
        return newBlockContext;
    }

    public boolean isNewValueContext() {
        return newValueContext;
    }

    public WrappedCollection getIncludeModel() {
        return includeModel;
    }
}
