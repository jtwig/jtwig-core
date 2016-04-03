package org.jtwig.model.tree;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;

public class BlockNode extends ContentNode {
    private final VariableExpression blockIdentifier;

    public BlockNode(Position position, VariableExpression blockIdentifier, Node content) {
        super(position, content);
        this.blockIdentifier = blockIdentifier;
    }

    public VariableExpression getBlockIdentifier() {
        return blockIdentifier;
    }

    public String getIdentifier() {
        return blockIdentifier.getIdentifier();
    }
}
