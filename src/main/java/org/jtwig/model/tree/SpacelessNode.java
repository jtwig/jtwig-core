package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.render.impl.StringRenderable;

public class SpacelessNode extends ContentNode {
    public SpacelessNode(Position position, Node content) {
        super(position, content);
    }

    @Override
    public Renderable render(RenderContext context) {
        StringBuilderRenderResult result = new StringBuilderRenderResult();
        String content = super.render(context).appendTo(result).content();
        String contentWithoutSpaces = context.environment().renderConfiguration().spaceRemover().apply(content);
        return new StringRenderable(contentWithoutSpaces, EscapeMode.NONE);
    }
}
