package org.jtwig.resource.resolver;

public class ResourceResolverFactory {
    public ResourceResolver create (ResourceResolverConfiguration configuration) {
        return new CompositeResourceResolver(configuration.getResourceResolvers());
    }
}
