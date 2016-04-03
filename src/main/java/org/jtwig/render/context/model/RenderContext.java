package org.jtwig.render.context.model;

import org.jtwig.render.context.StackedContext;
import org.jtwig.resource.Resource;
import org.jtwig.value.context.ValueContext;

public class RenderContext {
    private final StackedContext<ValueContext> valueContext;
    private final StackedContext<EscapeMode> escapeModeContext;
    private final StackedContext<Resource> resourceContext;
    private final StackedContext<BlockContext> blockContext;
    private final StackedContext<MacroDefinitionContext> macroDefinitionContext;
    private final StackedContext<MacroAliasesContext> macroAliasesContext;

    public RenderContext(StackedContext<ValueContext> valueContext, StackedContext<EscapeMode> escapeModeContext, StackedContext<Resource> resourceContext, StackedContext<BlockContext> blockContext, StackedContext<MacroDefinitionContext> macroDefinitionContext, StackedContext<MacroAliasesContext> macroAliasesContext) {
        this.valueContext = valueContext;
        this.escapeModeContext = escapeModeContext;
        this.resourceContext = resourceContext;
        this.blockContext = blockContext;
        this.macroDefinitionContext = macroDefinitionContext;
        this.macroAliasesContext = macroAliasesContext;
    }

    public StackedContext<ValueContext> getValueContext() {
        return valueContext;
    }
    public StackedContext<EscapeMode> getEscapeModeContext() {
        return escapeModeContext;
    }
    public StackedContext<Resource> getResourceContext() {
        return resourceContext;
    }
    public StackedContext<BlockContext> getBlockContext() {
        return blockContext;
    }
    public StackedContext<MacroDefinitionContext> getMacroDefinitionContext() {
        return macroDefinitionContext;
    }
    public StackedContext<MacroAliasesContext> getMacroAliasesContext() {
        return macroAliasesContext;
    }
}
