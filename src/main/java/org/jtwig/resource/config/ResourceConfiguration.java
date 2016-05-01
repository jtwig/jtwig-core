package org.jtwig.resource.config;

import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.reference.ResourceReferenceExtractor;
import org.jtwig.resource.resolver.RelativeResourceResolver;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

public class ResourceConfiguration {
    private final Collection<RelativeResourceResolver> relativeResourceResolvers;
    private final Collection<String> absoluteResourceTypes;
    private final List<TypedResourceLoader> resourceLoaders;
    private final ResourceReferenceExtractor resourceReferenceExtractor;
    private final Charset defaultCharset;

    public ResourceConfiguration(Collection<RelativeResourceResolver> relativeResourceResolvers, Collection<String> absoluteResourceTypes, List<TypedResourceLoader> resourceLoaders, ResourceReferenceExtractor resourceReferenceExtractor, Charset defaultCharset) {
        this.relativeResourceResolvers = relativeResourceResolvers;
        this.absoluteResourceTypes = absoluteResourceTypes;
        this.resourceLoaders = resourceLoaders;
        this.resourceReferenceExtractor = resourceReferenceExtractor;
        this.defaultCharset = defaultCharset;
    }

    public Collection<String> getAbsoluteResourceTypes() {
        return absoluteResourceTypes;
    }

    public List<TypedResourceLoader> getResourceLoaders() {
        return resourceLoaders;
    }

    public Collection<RelativeResourceResolver> getRelativeResourceResolvers() {
        return relativeResourceResolvers;
    }

    public ResourceReferenceExtractor getResourceReferenceExtractor() {
        return resourceReferenceExtractor;
    }

    public Charset getDefaultCharset() {
        return defaultCharset;
    }
}
