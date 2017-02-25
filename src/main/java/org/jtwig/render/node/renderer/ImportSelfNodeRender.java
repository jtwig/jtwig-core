package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.ImportSelfNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;

public class ImportSelfNodeRender implements NodeRender<ImportSelfNode> {
    @Override
    public Renderable render(final RenderRequest renderRequest, ImportSelfNode node) {
        String macroIdentifier = node.getAliasIdentifier().getIdentifier();
        MacroDefinitionContext macroDefinitionContext = MacroDefinitionContext.newContext();
        renderRequest.getRenderContext().getMacroDefinitionContext().start(macroDefinitionContext);

        final StackedContext<MacroAliasesContext> macroAliasesContext = renderRequest.getRenderContext().getMacroAliasesContext();
        if (macroAliasesContext.hasCurrent()) {
            macroAliasesContext.getCurrent().with(macroIdentifier, macroDefinitionContext);
        }
        macroAliasesContext.start(MacroAliasesContext.newContext());
        renderRequest.getRenderContext().getValueContext().getCurrent().with(macroIdentifier, macroDefinitionContext);

        renderRequest.getRenderContext().getResourceContext().getCurrent().onEnd(new Runnable(){
            @Override
            public void run() {
                macroAliasesContext.end();
                renderRequest.getRenderContext().getMacroDefinitionContext().end();
            }
        });


        return EmptyRenderable.instance();
    }
}
