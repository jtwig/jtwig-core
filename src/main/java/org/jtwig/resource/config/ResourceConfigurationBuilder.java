package org.jtwig.resource.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.reference.ResourceReferenceExtractor;
import org.jtwig.resource.resolver.RelativeResourceResolver;
import org.jtwig.util.builder.ListBuilder;
import org.jtwig.util.builder.MapBuilder;

import java.nio.charset.Charset;

public class ResourceConfigurationBuilder<B extends ResourceConfigurationBuilder> implements Builder<ResourceConfiguration> {
    private final ListBuilder<B, RelativeResourceResolver> resourceResolvers;
    private final MapBuilder<B, String, ResourceLoader> resourceLoaders;
    private final ListBuilder<B, String> absoluteResourceTypes;
    private ResourceReferenceExtractor resourceReferenceExtractor;
    private Charset defaultInputCharset;

    public ResourceConfigurationBuilder() {
        this.resourceResolvers = new ListBuilder<>(self());
        this.resourceLoaders = new MapBuilder<>(self());
        this.absoluteResourceTypes = new ListBuilder<>(self());
    }

    public ResourceConfigurationBuilder(ResourceConfiguration prototype) {
        this.resourceResolvers = new ListBuilder<>(self(), prototype.getRelativeResourceResolvers());
        this.resourceLoaders = new MapBuilder<>(self(), prototype.getResourceLoaders());
        this.absoluteResourceTypes = new ListBuilder<>(self(), prototype.getAbsoluteResourceTypes());
        this.defaultInputCharset = prototype.getDefaultCharset();
        this.resourceReferenceExtractor = prototype.getResourceReferenceExtractor();
    }

    public B withDefaultInputCharset(Charset defaultInputCharset) {
        this.defaultInputCharset = defaultInputCharset;
        return self();
    }

    public B withResourceReferenceExtractor(ResourceReferenceExtractor extractor) {
        this.resourceReferenceExtractor = extractor;
        return self();
    }

    public ListBuilder<B, RelativeResourceResolver> resourceResolvers() {
        return resourceResolvers;
    }

    public MapBuilder<B, String, ResourceLoader> resourceLoaders () {
        return resourceLoaders;
    }

    public ListBuilder<B, String> absoluteResourceTypes() {
        return absoluteResourceTypes;
    }

    private B self() {
        return (B) this;
    }

    @Override
    public ResourceConfiguration build() {
        return new ResourceConfiguration(resourceResolvers.build(),
                absoluteResourceTypes.build(),
                resourceLoaders.build(),
                resourceReferenceExtractor,
                defaultInputCharset);
    }
}
