package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.ImportSelfNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.ValueContext;

public class ImportSelfNodeRender implements NodeRender<ImportSelfNode> {
    @Override
    public Renderable render(final RenderRequest renderRequest, ImportSelfNode node) {
        String macroIdentifier = node.getAliasIdentifier().getIdentifier();
        MacroDefinitionContext macroDefinitionContext = MacroDefinitionContext.newContext();
        renderRequest.getRenderContext().start(MacroDefinitionContext.class, macroDefinitionContext);

        if (renderRequest.getRenderContext().hasCurrent(MacroAliasesContext.class)) {
            renderRequest.getRenderContext().getCurrent(MacroAliasesContext.class).with(macroIdentifier, macroDefinitionContext);
        }

        renderRequest.getRenderContext().start(MacroAliasesContext.class, MacroAliasesContext.newContext());
        renderRequest.getRenderContext().getCurrent(ValueContext.class).with(macroIdentifier, macroDefinitionContext);

        renderRequest.getRenderContext().onEndCurrent(ResourceReference.class, new Runnable(){
            @Override
            public void run() {
                renderRequest.getRenderContext().end(MacroAliasesContext.class);
                renderRequest.getRenderContext().end(MacroDefinitionContext.class);
            }
        });


        return EmptyRenderable.instance();
    }
}
