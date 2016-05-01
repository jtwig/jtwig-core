package org.jtwig.resource;

import com.google.common.base.Optional;
import org.jtwig.resource.exceptions.ResourceException;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.metadata.EmptyResourceMetadata;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.metadata.ResourceResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.reference.ResourceReferenceExtractor;
import org.jtwig.resource.resolver.RelativeResourceResolver;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ResourceService {
    private final Map<String, ResourceLoader> loaderMap;
    private final List<TypedResourceLoader> loaderList;
    private final Collection<String> absoluteResourceTypes;
    private final Collection<RelativeResourceResolver> relativeResourceResolvers;
    private final ResourceReferenceExtractor resourceReferenceExtractor;

    public ResourceService(Map<String, ResourceLoader> loaderMap, List<TypedResourceLoader> loaderList, Collection<String> absoluteResourceTypes, Collection<RelativeResourceResolver> relativeResourceResolvers, ResourceReferenceExtractor resourceReferenceExtractor) {
        this.loaderMap = loaderMap;
        this.loaderList = loaderList;
        this.absoluteResourceTypes = absoluteResourceTypes;
        this.relativeResourceResolvers = relativeResourceResolvers;
        this.resourceReferenceExtractor = resourceReferenceExtractor;
    }

    public ResourceReference resolve(ResourceReference current, String path) {
        ResourceReference resourceReference = resourceReferenceExtractor.extract(path);

        if (absoluteResourceTypes.contains(current.getType()) || absoluteResourceTypes.contains(resourceReference.getType())) {
            return resourceReference;
        } else {
            if (ResourceReference.ANY_TYPE.equals(resourceReference.getType()) || resourceReference.getType().equals(current.getType())) {
                for (RelativeResourceResolver resourceResolver : relativeResourceResolvers) {
                    Optional<ResourceReference> referenceOptional = resourceResolver.resolve(current, resourceReference);
                    if (referenceOptional.isPresent()) return referenceOptional.get();
                }
            }
        }

        return resourceReference;
    }

    public ResourceMetadata loadMetadata(ResourceReference reference) {
        ResourceLoader result;
        if (ResourceReference.ANY_TYPE.equals(reference.getType())) {
            Optional<ResourceLoader> resourceLoader = getFirstExistingResourceLoader(reference);
            if (!resourceLoader.isPresent())
                return EmptyResourceMetadata.instance();
            else
                result = resourceLoader.get();
        } else {
            Optional<ResourceLoader> loaderOptional = Optional.fromNullable(loaderMap.get(reference.getType()));
            if (!loaderOptional.isPresent()) {
                throw new ResourceException(String.format("Resource loader for type '%s' not configured", reference.getType()));
            } else {
                result = loaderOptional.get();
            }
        }
        return new ResourceResourceMetadata(result, reference);
    }

    private Optional<ResourceLoader> getFirstExistingResourceLoader(ResourceReference reference) {
        for (TypedResourceLoader typedResourceLoader : loaderList) {
            if (!ResourceReference.STRING.equals(typedResourceLoader.getType()) && typedResourceLoader.getResourceLoader().exists(reference.getPath())) {
                return Optional.of(typedResourceLoader.getResourceLoader());
            }
        }
        return Optional.absent();
    }
}
