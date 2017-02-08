package org.jtwig.property.environment;

import org.jtwig.property.configuration.PropertyResolverConfiguration;
import org.jtwig.property.resolver.request.ArgumentsExtractor;
import org.jtwig.property.resolver.request.PropertyNameExtractor;
import org.jtwig.property.resolver.request.PropertyResolveRequestFactory;
import org.jtwig.property.selection.BaseSelectionPropertyResolver;
import org.jtwig.property.selection.CachedSelectionPropertyResolver;
import org.jtwig.property.selection.SelectionPropertyResolveService;

public class PropertyResolverEnvironmentFactory {
    public PropertyResolverEnvironment create (PropertyResolverConfiguration configuration) {
        SelectionPropertyResolveService selectionPropertyResolveService = new SelectionPropertyResolveService(new PropertyResolveRequestFactory(new PropertyNameExtractor(), new ArgumentsExtractor()));
        BaseSelectionPropertyResolver selectionPropertyResolver = new BaseSelectionPropertyResolver(configuration.getPropertyResolverStrategies(), selectionPropertyResolveService);
        return new PropertyResolverEnvironment(
                new CachedSelectionPropertyResolver(configuration.getCache(), selectionPropertyResolver, selectionPropertyResolveService)
        );
    }
}
