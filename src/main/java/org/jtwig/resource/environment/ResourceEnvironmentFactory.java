package org.jtwig.resource.environment;

import org.jtwig.resource.ResourceService;
import org.jtwig.resource.config.ResourceConfiguration;

public class ResourceEnvironmentFactory {
    public ResourceEnvironment create(ResourceConfiguration resourceConfiguration) {
        ResourceService resourceService = new ResourceService(resourceConfiguration.getResourceLoaders(),
                resourceConfiguration.getAbsoluteResourceTypes(),
                resourceConfiguration.getRelativeResourceResolvers(),
                resourceConfiguration.getResourceReferenceExtractor());
        return new ResourceEnvironment(resourceConfiguration.getDefaultCharset(), resourceService);
    }
}
