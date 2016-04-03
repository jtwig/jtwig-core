package org.jtwig.model.tree;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;

public class OverrideBlockNode extends BlockNode {
    public OverrideBlockNode(Position position, VariableExpression blockIdentifier, Node content) {
        super(position, blockIdentifier, content);
    }
}
