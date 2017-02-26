package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.macro.ImportedMacros;
import org.jtwig.macro.render.MacroRender;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.property.resolver.MacroPropertyResolver;
import org.jtwig.property.resolver.PropertyResolver;

public class MacroPropertyResolverStrategy implements PropertyResolverStrategy {
    private final MacroRender macroRender;

    public MacroPropertyResolverStrategy(MacroRender macroRender) {
        this.macroRender = macroRender;
    }

    @Override
    public Optional<PropertyResolver> select(Request request) {
        if (request.getLeftValue() instanceof ImportedMacros) {
            if (request.getRightExpression() instanceof FunctionExpression) {
                ImportedMacros importedMacros = (ImportedMacros) request.getLeftValue();
                PropertyResolver propertyResolver = new MacroPropertyResolver(macroRender, importedMacros);
                return Optional.of(propertyResolver);
            }
        }
        return Optional.absent();
    }
}
