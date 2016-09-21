package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.resolver.path.RelativeReferenceResolver;

import java.util.Collection;

public class ReferenceRelativeResourceResolver implements RelativeResourceResolver {
    private final Collection<String> pathTypes;
    private final RelativeReferenceResolver relativePathResolver;

    public ReferenceRelativeResourceResolver(Collection<String> pathTypes, RelativeReferenceResolver relativePathResolver) {
        this.pathTypes = pathTypes;
        this.relativePathResolver = relativePathResolver;
    }

    @Override
    public Optional<ResourceReference> resolve(ResourceReference parentReference, ResourceReference newReference) {
        if (pathTypes.contains(parentReference.getType())) {
            if (relativePathResolver.isRelative(newReference.getPath())) {
                return Optional.of(new ResourceReference(parentReference.getType(), relativePathResolver.resolve(parentReference.getPath(), newReference.getPath())));
            } else {
                return Optional.of(newReference);
            }
        } else {
            return Optional.absent();
        }
    }
}
