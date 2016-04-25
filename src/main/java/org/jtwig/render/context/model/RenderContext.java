package org.jtwig.render.context.model;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.render.context.StackedContext;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.ValueContext;

public class RenderContext {
    private final StackedContext<ValueContext> valueContext;
    private final StackedContext<EscapeEngine> escapeEngineContext;
    private final StackedContext<ResourceReference> resourceContext;
    private final StackedContext<BlockContext> blockContext;
    private final StackedContext<MacroDefinitionContext> macroDefinitionContext;
    private final StackedContext<MacroAliasesContext> macroAliasesContext;

    public RenderContext(StackedContext<ValueContext> valueContext, StackedContext<EscapeEngine> escapeEngineContext, StackedContext<ResourceReference> resourceContext, StackedContext<BlockContext> blockContext, StackedContext<MacroDefinitionContext> macroDefinitionContext, StackedContext<MacroAliasesContext> macroAliasesContext) {
        this.valueContext = valueContext;
        this.escapeEngineContext = escapeEngineContext;
        this.resourceContext = resourceContext;
        this.blockContext = blockContext;
        this.macroDefinitionContext = macroDefinitionContext;
        this.macroAliasesContext = macroAliasesContext;
    }

    public StackedContext<ValueContext> getValueContext() {
        return valueContext;
    }
    public StackedContext<EscapeEngine> getEscapeEngineContext() {
        return escapeEngineContext;
    }
    public StackedContext<ResourceReference> getResourceContext() {
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
