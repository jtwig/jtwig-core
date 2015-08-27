package org.jtwig.resource.resolver;

import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.Collection;

public class ResourceResolverConfigurationBuilder<B extends ResourceResolverConfigurationBuilder> implements Builder<ResourceResolverConfiguration> {
    private final Collection<ResourceResolver> resourceResolvers = new ArrayList<>();

    public ResourceResolverConfigurationBuilder () {}
    public ResourceResolverConfigurationBuilder (ResourceResolverConfiguration prototype) {
        resourceResolvers.addAll(prototype.getResourceResolvers());
    }

    public B withResourceResolver (ResourceResolver resourceResolver) {
        this.resourceResolvers.add(resourceResolver);
        return self();
    }

    public B withResourceResolvers(Collection<ResourceResolver> resourceResolvers) {
        this.resourceResolvers.addAll(resourceResolvers);
        return self();
    }

    protected B self () {
        return (B) this;
    }

    @Override
    public ResourceResolverConfiguration build() {
        return new ResourceResolverConfiguration(resourceResolvers);
    }
}
