package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.Resource;

import java.util.Map;

public class InMemoryResourceResolver implements ResourceResolver {
    private final Map<String, Resource> resourceMap;

    public InMemoryResourceResolver(Map<String, Resource> resourceMap) {
        this.resourceMap = resourceMap;
    }

    @Override
    public Optional<Resource> resolve(Environment environment, Resource resource, String path) {
        return Optional.fromNullable(resourceMap.get(path));
    }
}
