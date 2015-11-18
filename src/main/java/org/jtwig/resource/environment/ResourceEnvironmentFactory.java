package org.jtwig.resource.environment;

import org.jtwig.resource.config.ResourceConfiguration;
import org.jtwig.resource.resolver.CompositeResourceResolver;

public class ResourceEnvironmentFactory {
    public ResourceEnvironment create(ResourceConfiguration resourceConfiguration) {
        return new ResourceEnvironment(new CompositeResourceResolver(resourceConfiguration.getResourceResolvers()), resourceConfiguration.getDefaultCharset());
    }
}
