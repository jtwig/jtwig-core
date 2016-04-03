package org.jtwig.render.context.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MacroAliasesContext implements Iterable<Map.Entry<String, MacroDefinitionContext>> {
    public static MacroAliasesContext newContext () {
        return new MacroAliasesContext(new HashMap<String, MacroDefinitionContext>());
    }

    private final Map<String, MacroDefinitionContext> macros;

    public MacroAliasesContext (Map<String, MacroDefinitionContext> macros) {
        this.macros = macros;
    }

    public MacroAliasesContext with (String identifier, MacroDefinitionContext context) {
        macros.put(identifier, context);
        return this;
    }

    @Override
    public Iterator<Map.Entry<String, MacroDefinitionContext>> iterator() {
        return macros.entrySet().iterator();
    }
}
