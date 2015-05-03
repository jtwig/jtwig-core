package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.FutureRenderable;

public class BlockNode extends ContentNode {
    private final VariableExpression blockIdentifier;
    private final Node content;


    public BlockNode(Position position, VariableExpression blockIdentifier, Node content) {
        super(position, content);
        this.blockIdentifier = blockIdentifier;
        this.content = content;
    }

    public VariableExpression getBlockIdentifier() {
        return blockIdentifier;
    }

    public Node getContent() {
        return content;
    }

    @Override
    public Renderable render(RenderContext context) {
        FutureRenderable futureRenderable = new FutureRenderable();
        Renderable renderable = context.currentResource().register(blockIdentifier.getIdentifier(), futureRenderable);
        futureRenderable.complete(super.render(context));
        context.currentResource().endBlock();
        return renderable;
    }
}
