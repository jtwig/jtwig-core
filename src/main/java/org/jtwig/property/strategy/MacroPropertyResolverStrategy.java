package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.property.macro.MacroRender;
import org.jtwig.property.resolver.MacroPropertyResolver;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.render.context.model.MacroDefinitionContext;

public class MacroPropertyResolverStrategy implements PropertyResolverStrategy {
    private final MacroRender macroRender;

    public MacroPropertyResolverStrategy(MacroRender macroRender) {
        this.macroRender = macroRender;
    }

    @Override
    public Optional<PropertyResolver> select(Request request) {
        if (request.getLeftValue() instanceof MacroDefinitionContext) {
            if (request.getRightExpression() instanceof FunctionExpression) {
                MacroDefinitionContext macroDefinitionContext = (MacroDefinitionContext) request.getLeftValue();
                PropertyResolver propertyResolver = new MacroPropertyResolver(macroRender, macroDefinitionContext);
                return Optional.of(propertyResolver);
            }
        }
        return Optional.absent();
    }
}
