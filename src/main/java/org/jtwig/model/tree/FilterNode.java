package org.jtwig.model.tree;

import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.model.position.Position;

public class FilterNode extends ContentNode {
    private final InjectableExpression filterExpression;

    public FilterNode(Position position, Node content, InjectableExpression filterExpression) {
        super(position, content);
        this.filterExpression = filterExpression;
    }

    public InjectableExpression getFilterExpression() {
        return filterExpression;
    }
}
