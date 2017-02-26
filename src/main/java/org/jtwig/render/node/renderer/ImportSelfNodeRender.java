package org.jtwig.render.node.renderer;

import org.jtwig.macro.render.ImportRender;
import org.jtwig.model.tree.ImportSelfNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.reference.ResourceReference;

public class ImportSelfNodeRender implements NodeRender<ImportSelfNode> {
    private final ImportRender importRender;

    public ImportSelfNodeRender(ImportRender importRender) {
        this.importRender = importRender;
    }

    @Override
    public Renderable render(RenderRequest renderRequest, ImportSelfNode node) {
        importRender.render(renderRequest,
                renderRequest.getRenderContext().getCurrent(ResourceReference.class),
                node.getAliasIdentifier().getIdentifier()
        );

        return EmptyRenderable.instance();
    }
}
