package org.jtwig.resource.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.util.builder.ListBuilder;

import java.nio.charset.Charset;

public class ResourceConfigurationBuilder<B extends ResourceConfigurationBuilder> implements Builder<ResourceConfiguration> {
    private final ListBuilder<B, ResourceResolver> resourceResolvers;
    private Charset defaultInputCharset;

    public ResourceConfigurationBuilder() {
        this.resourceResolvers = new ListBuilder<>(self());
    }

    public ResourceConfigurationBuilder(ResourceConfiguration prototype) {
        this.resourceResolvers = new ListBuilder<>(self());
        this.defaultInputCharset = prototype.getDefaultCharset();
    }

    public B withDefaultInputCharset(Charset defaultInputCharset) {
        this.defaultInputCharset = defaultInputCharset;
        return self();
    }

    public ListBuilder<B, ResourceResolver> resourceResolvers() {
        return resourceResolvers;
    }

    private B self() {
        return (B) this;
    }

    @Override
    public ResourceConfiguration build() {
        return new ResourceConfiguration(resourceResolvers.build(), defaultInputCharset);
    }
}
