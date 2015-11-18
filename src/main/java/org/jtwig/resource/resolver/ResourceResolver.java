package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.resource.Resource;

public interface ResourceResolver {
    Optional<Resource> resolve(Environment environment, Resource resource, String path);
}
