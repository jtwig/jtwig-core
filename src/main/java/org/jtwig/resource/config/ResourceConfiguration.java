package org.jtwig.resource.config;

import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.reference.ResourceReferenceExtractor;
import org.jtwig.resource.resolver.RelativeResourceResolver;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;

public class ResourceConfiguration {
    private final Collection<RelativeResourceResolver> relativeResourceResolvers;
    private final Collection<String> absoluteResourceTypes;
    private final Map<String, ResourceLoader> resourceLoaderMap;
    private final ResourceReferenceExtractor resourceReferenceExtractor;
    private final Charset defaultCharset;

    public ResourceConfiguration(Collection<RelativeResourceResolver> relativeResourceResolvers, Collection<String> absoluteResourceTypes, Map<String, ResourceLoader> resourceLoaderMap, ResourceReferenceExtractor resourceReferenceExtractor, Charset defaultCharset) {
        this.relativeResourceResolvers = relativeResourceResolvers;
        this.absoluteResourceTypes = absoluteResourceTypes;
        this.resourceLoaderMap = resourceLoaderMap;
        this.resourceReferenceExtractor = resourceReferenceExtractor;
        this.defaultCharset = defaultCharset;
    }

    public Collection<String> getAbsoluteResourceTypes() {
        return absoluteResourceTypes;
    }

    public Map<String, ResourceLoader> getResourceLoaders() {
        return resourceLoaderMap;
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
