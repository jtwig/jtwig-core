package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.MacroNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacroNodeRender implements NodeRender<MacroNode> {
    private final static Logger log = LoggerFactory.getLogger(MacroNodeRender.class);

    @Override
    public Renderable render(RenderRequest request, MacroNode node) {
        return EmptyRenderable.instance();
    }
}
