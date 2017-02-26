package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.macro.ImportedMacros;
import org.jtwig.macro.Macro;
import org.jtwig.macro.render.MacroRender;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;

public class MacroPropertyResolver implements PropertyResolver {
    private final MacroRender macroRender;
    private final ImportedMacros macro;

    public MacroPropertyResolver(MacroRender macroRender, ImportedMacros macro) {
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
