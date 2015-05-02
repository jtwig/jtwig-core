package org.jtwig.resource.resolver;

import com.google.common.base.Optional;

import org.jtwig.resource.Resource;

public interface ResourceResolver {
    Optional<Resource> resolve(Resource resource, String relativePath);
}
