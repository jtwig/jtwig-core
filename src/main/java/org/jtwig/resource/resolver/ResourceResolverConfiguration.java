package org.jtwig.resource.resolver;

import java.util.Collection;

public class ResourceResolverConfiguration {
    private final Collection<ResourceResolver> resourceResolvers;

    public ResourceResolverConfiguration(Collection<ResourceResolver> resourceResolvers) {
        this.resourceResolvers = resourceResolvers;
    }

    public Collection<ResourceResolver> getResourceResolvers() {
        return resourceResolvers;
    }
}
