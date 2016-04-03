package org.jtwig.render.context.model;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

public class MacroDefinitionContext {
    public static MacroDefinitionContext newContext () {
        return new MacroDefinitionContext(new HashMap<String, Macro>());
    }

    private final Map<String, Macro> macros;

    public MacroDefinitionContext(Map<String, Macro> macros) {
        this.macros = macros;
    }

    public Optional<Macro> resolve(String name) {
        return Optional.fromNullable(macros.get(name));
    }

    public MacroDefinitionContext add (String identifier, Macro macro) {
        macros.put(identifier, macro);
        return this;
    }
}
