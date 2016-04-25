package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.resource.resolver.path.RelativePathResolver;

import java.util.Collection;

public class PathRelativeResourceResolver implements RelativeResourceResolver {
    private final Collection<String> filePathTypes;
    private final RelativePathResolver relativePathResolver;

    public PathRelativeResourceResolver(Collection<String> filePathTypes, RelativePathResolver relativePathResolver) {
        this.filePathTypes = filePathTypes;
        this.relativePathResolver = relativePathResolver;
    }

    @Override
    public Optional<ResourceReference> resolve(ResourceReference parentReference, ResourceReference newReference) {
        if (filePathTypes.contains(parentReference.getType())) {
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
