package org.jtwig.resource.config;

import org.jtwig.resource.resolver.ResourceResolver;

import java.nio.charset.Charset;
import java.util.Collection;

public class ResourceConfiguration {
    private final Collection<ResourceResolver> resourceResolvers;
    private final Charset defaultCharset;

    public ResourceConfiguration(Collection<ResourceResolver> resourceResolvers, Charset defaultCharset) {
        this.resourceResolvers = resourceResolvers;
        this.defaultCharset = defaultCharset;
    }

    public Collection<ResourceResolver> getResourceResolvers() {
        return resourceResolvers;
    }

    public Charset getDefaultCharset() {
        return defaultCharset;
    }
}
