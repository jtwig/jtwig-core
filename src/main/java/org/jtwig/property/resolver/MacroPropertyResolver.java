package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.macro.MacroRender;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.context.model.Macro;
import org.jtwig.render.context.model.MacroDefinitionContext;

public class MacroPropertyResolver implements PropertyResolver {
    private final MacroRender macroRender;
    private final MacroDefinitionContext macro;

    public MacroPropertyResolver(MacroRender macroRender, MacroDefinitionContext macro) {
        this.macroRender = macroRender;
        this.macro = macro;
    }

    @Override
    public Optional<Value> resolve(PropertyResolveRequest request) {
        Optional<Macro> resolve = macro.resolve(request.getPropertyName().get());
        if (!resolve.isPresent()) return Optional.absent();

        return Optional.of(new Value(macroRender.render(request, request.getArguments(), resolve.get())));
    }
}
