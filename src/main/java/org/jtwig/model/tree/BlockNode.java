package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;

public class BlockNode extends ContentNode {
    private final VariableExpression blockIdentifier;

    public BlockNode(Position position, VariableExpression blockIdentifier, Node content) {
        super(position, content);
        this.blockIdentifier = blockIdentifier;
    }

    public VariableExpression getBlockIdentifier() {
        return blockIdentifier;
    }

    @Override
    public Renderable render(RenderContext context) {
        ResourceContext resourceContext = context.currentResource();
        Optional<Renderable> block = resourceContext.block(getIdentifier());
        if (block.isPresent()) {
            return block.get();
        } else {
            Renderable renderable = getContent().render(context);
            resourceContext
                    .startBlock(getIdentifier())
                    .endBlock(renderable);
            return renderable;
        }
    }

    public String getIdentifier() {
        return blockIdentifier.getIdentifier();
    }
}
