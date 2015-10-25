package org.jtwig.resource.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.resource.resolver.ResourceResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

public class ResourceConfigurationBuilder<B extends ResourceConfigurationBuilder> implements Builder<ResourceConfiguration> {
    private final Collection<ResourceResolver> resourceResolvers = new ArrayList<>();
    private Charset defaultInputCharset;

    public ResourceConfigurationBuilder() {}

    public ResourceConfigurationBuilder(ResourceConfiguration prototype) {
        this.resourceResolvers.addAll(prototype.getResourceResolvers());
        this.defaultInputCharset = prototype.getDefaultCharset();
    }

    public B withDefaultInputCharset(Charset defaultInputCharset) {
        this.defaultInputCharset = defaultInputCharset;
        return self();
    }

    public B withResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolvers.add(resourceResolver);
        return self();
    }

    public B withResourceResolvers(Collection<ResourceResolver> resourceResolvers) {
        this.resourceResolvers.addAll(resourceResolvers);
        return self();
    }

    private B self() {
        return (B) this;
    }

    @Override
    public ResourceConfiguration build() {
        return new ResourceConfiguration(resourceResolvers, defaultInputCharset);
    }
}
