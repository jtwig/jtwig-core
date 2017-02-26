package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.VerbatimNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;

public class VerbatimNodeRender implements NodeRender<VerbatimNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, VerbatimNode node) {
        return new StringRenderable(node.getContent());
    }
}
