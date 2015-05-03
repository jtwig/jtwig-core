package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.EmptyRenderable;

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

    @Override
    public Renderable render(RenderContext context) {
        for (IfConditionNode conditionNode : conditionNodes) {
            if (conditionNode.isTrue(context)) {
                return conditionNode.render(context);
            }
        }
        return EmptyRenderable.instance();
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

        public boolean isTrue(RenderContext context) {
            return condition.calculate(context).asBoolean();
        }
    }

}
