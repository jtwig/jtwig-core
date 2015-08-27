package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.EmptyRenderable;

public class OverrideBlockNode extends BlockNode {
    public OverrideBlockNode(Position position, VariableExpression blockIdentifier, Node content) {
        super(position, blockIdentifier, content);
    }

    @Override
    public Renderable render(RenderContext context) {
        Optional<Renderable> block = context.currentResource().block(getIdentifier());
        if (!block.isPresent()) {
            context.currentResource().startBlock(getIdentifier())
                    .endBlock(getContent().render(context));
        }
        return EmptyRenderable.instance();
    }
}
