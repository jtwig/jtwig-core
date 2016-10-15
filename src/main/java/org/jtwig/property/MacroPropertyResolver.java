package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.property.macro.MacroRender;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.context.model.Macro;
import org.jtwig.render.context.model.MacroDefinitionContext;

public class MacroPropertyResolver implements PropertyResolver {
    private final MacroRender macroRender;

    public MacroPropertyResolver(MacroRender macroRender) {
        this.macroRender = macroRender;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        if (request.getEntity() == null) return Optional.absent();

        if (request.getEntity().getValue() instanceof MacroDefinitionContext) {
            MacroDefinitionContext macroDefinitionContext = (MacroDefinitionContext) request.getEntity().getValue();
            Optional<Macro> macroOptional = macroDefinitionContext.resolve(request.getPropertyName());

            if (macroOptional.isPresent()) {
                return Optional.of(new Value(macroRender.render(request, macroOptional.get())));
            } else {
                return Optional.absent();
            }
        } else {
            return Optional.absent();
        }
    }
}
