package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

import java.util.Collection;

public class IfNode extends Node {
    private final Collection<IfConditionNode> conditionNodes;

    public IfNode(Position position, Collection<IfConditionNode> conditionNodes) {
        super(position);
        this.conditionNodes = conditionNodes;
    }

    public Collection<IfConditionNode> getConditionNodes() {
        return conditionNodes;
    }

    public static class IfConditionNode extends ContentNode {
        private final Expression condition;

        public IfConditionNode(Position position, Expression condition, Node content) {
            super(position, content);
            this.condition = condition;
        }

        public Expression getCondition() {
            return condition;
        }
    }
}
