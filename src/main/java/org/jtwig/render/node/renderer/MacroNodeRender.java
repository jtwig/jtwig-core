package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.MacroNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.Macro;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacroNodeRender implements NodeRender<MacroNode> {
    private final static Logger log = LoggerFactory.getLogger(MacroNodeRender.class);

    @Override
    public Renderable render(RenderRequest request, MacroNode node) {
        StackedContext<MacroDefinitionContext> macroDefinitionContext = request.getRenderContext().getMacroDefinitionContext();
        if (macroDefinitionContext.hasCurrent()) {
            StackedContext<MacroAliasesContext> macroContext = request.getRenderContext().getMacroAliasesContext();
            macroDefinitionContext.getCurrent().add(node.getMacroName().getIdentifier(), new Macro(
                    macroContext.hasCurrent() ? macroContext.getCurrent() : MacroAliasesContext.newContext(),
                    node.getMacroArgumentNames(),
                    node.getContent()
            ));
        } else {
            log.warn("Macro {} defined without context", node.getMacroName());
        }
        return EmptyRenderable.instance();
    }
}
