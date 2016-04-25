package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.reference.ResourceReference;

public interface RelativeResourceResolver {
    Optional<ResourceReference> resolve(ResourceReference parentReference, ResourceReference newPath);
}
