package org.jtwig.property.selection;

import com.google.common.base.Optional;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCache;

public class CachedSelectionPropertyResolver implements SelectionPropertyResolver {
    private final SelectionPropertyResolverCache selectionPropertyResolverCache;
    private final SelectionPropertyResolver delegate;
    private final SelectionPropertyResolveService selectionPropertyResolveService;

    public CachedSelectionPropertyResolver(SelectionPropertyResolverCache selectionPropertyResolverCache, SelectionPropertyResolver delegate, SelectionPropertyResolveService selectionPropertyResolveService) {
        this.selectionPropertyResolverCache = selectionPropertyResolverCache;
        this.delegate = delegate;
        this.selectionPropertyResolveService = selectionPropertyResolveService;
    }

    @Override
    public SelectionResult resolve(SelectionRequest request) {
        Optional<PropertyResolver> result = selectionPropertyResolverCache.getCachedResolver(request.getRightExpression());
        if (result.isPresent()) {
            PropertyResolver propertyResolver = result.get();
            Object leftValue = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, request.getLeftExpression());
            return selectionPropertyResolveService.resolve(propertyResolver, request, leftValue);
        } else {
            SelectionResult cacheMissResult = delegate.resolve(request);
            if (cacheMissResult.getPropertyResolver().isPresent()) {
                selectionPropertyResolverCache.cacheResolver(request.getRightExpression(), cacheMissResult.getPropertyResolver().get());
            }
            return cacheMissResult;
        }
    }
}
