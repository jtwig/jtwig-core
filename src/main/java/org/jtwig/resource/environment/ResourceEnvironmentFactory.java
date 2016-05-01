package org.jtwig.resource.environment;

import org.jtwig.resource.ResourceService;
import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.resource.loader.CompositeResourceLoader;
import org.jtwig.resource.loader.ResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceEnvironmentFactory {
    public ResourceEnvironment create(ResourceConfiguration resourceConfiguration) {
        List<TypedResourceLoader> resourceLoaders = resourceConfiguration.getResourceLoaders();
        Map<String, ResourceLoader> resourceLoaderMap = resourceLoaderListToMap(resourceLoaders);

        ResourceService resourceService = new ResourceService(
                resourceLoaderMap,
                resourceLoaders,
                resourceConfiguration.getAbsoluteResourceTypes(),
                resourceConfiguration.getRelativeResourceResolvers(),
                resourceConfiguration.getResourceReferenceExtractor());
        return new ResourceEnvironment(resourceConfiguration.getDefaultCharset(), resourceService, resourceConfiguration.getResourceReferenceExtractor());
    }

    private Map<String, ResourceLoader> resourceLoaderListToMap(List<TypedResourceLoader> resourceLoaders) {
        Map<String, List<ResourceLoader>> loaders = new HashMap<>();
        for (TypedResourceLoader resourceLoader : resourceLoaders) {
            if (!loaders.containsKey(resourceLoader.getType())) {
                loaders.put(resourceLoader.getType(), new ArrayList<ResourceLoader>());
            }

            loaders.get(resourceLoader.getType()).add(resourceLoader.getResourceLoader());
        }

        Map<String, ResourceLoader> result = new HashMap<>();
        for (Map.Entry<String, List<ResourceLoader>> entry : loaders.entrySet()) {
            if (entry.getValue().size() <= 1) {
                result.put(entry.getKey(), entry.getValue().get(0));
            } else {
                result.put(entry.getKey(), new CompositeResourceLoader(entry.getValue()));
            }
        }

        return result;
    }
}
