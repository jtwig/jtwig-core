package org.jtwig.render.context.model;

import org.jtwig.model.tree.Node;

import java.util.Collection;

public class Macro {
    private final MacroAliasesContext macroAliasesContext;
    private final Collection<String> argumentNames;
    private final Node content;


    public Macro(MacroAliasesContext macroAliasesContext, Collection<String> argumentNames, Node content) {
        this.macroAliasesContext = macroAliasesContext;
        this.argumentNames = argumentNames;
        this.content = content;
    }

    public MacroAliasesContext getMacroAliasesContext() {
        return macroAliasesContext;
    }

    public Collection<String> getArgumentNames() {
        return argumentNames;
    }

    public Node getContent() {
        return content;
    }
}
