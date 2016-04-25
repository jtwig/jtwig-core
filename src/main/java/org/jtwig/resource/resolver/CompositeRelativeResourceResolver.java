package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.reference.ResourceReference;

import java.util.Collection;

public class CompositeRelativeResourceResolver implements RelativeResourceResolver {
    private final Collection<RelativeResourceResolver> relativeResourceResolvers;

    public CompositeRelativeResourceResolver(Collection<RelativeResourceResolver> relativeResourceResolvers) {
        this.relativeResourceResolvers = relativeResourceResolvers;
    }

    @Override
    public Optional<ResourceReference> resolve(ResourceReference parentReference, ResourceReference newReference) {
        for (RelativeResourceResolver relativeResourceResolver : relativeResourceResolvers) {
            Optional<ResourceReference> result = relativeResourceResolver.resolve(parentReference, newReference);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.absent();
    }
}
