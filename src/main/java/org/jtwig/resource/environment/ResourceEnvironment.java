package org.jtwig.resource.environment;

import org.jtwig.resource.ResourceService;

import java.nio.charset.Charset;

public class ResourceEnvironment {
    private final Charset defaultInputCharset;
    private final ResourceService resourceService;

    public ResourceEnvironment(Charset defaultInputCharset, ResourceService resourceService) {
        this.defaultInputCharset = defaultInputCharset;
        this.resourceService = resourceService;
    }

    public Charset getDefaultInputCharset() {
        return defaultInputCharset;
    }

    public ResourceService getResourceService() {
        return resourceService;
    }
}
