package org.jtwig.resource.environment;

import org.jtwig.resource.resolver.ResourceResolver;

import java.nio.charset.Charset;

public class ResourceEnvironment {
    private final ResourceResolver resourceResolver;
    private final Charset defaultInputCharset;

    public ResourceEnvironment(ResourceResolver resourceResolver, Charset defaultInputCharset) {
        this.resourceResolver = resourceResolver;
        this.defaultInputCharset = defaultInputCharset;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public Charset getDefaultInputCharset() {
        return defaultInputCharset;
    }
}
