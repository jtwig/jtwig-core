package org.jtwig.property.environment;

import org.jtwig.property.selection.SelectionPropertyResolver;

public class PropertyResolverEnvironment {
    private final SelectionPropertyResolver selectionPropertyResolver;

    public PropertyResolverEnvironment(SelectionPropertyResolver selectionPropertyResolver) {
        this.selectionPropertyResolver = selectionPropertyResolver;
    }

    public SelectionPropertyResolver getSelectionPropertyResolver() {
        return selectionPropertyResolver;
    }
}
