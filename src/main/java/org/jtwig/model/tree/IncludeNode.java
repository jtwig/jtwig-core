package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;

public class IncludeNode extends Node {
    private final IncludeConfiguration configuration;

    public IncludeNode(Position position, IncludeConfiguration configuration) {
        super(position);
        this.configuration = configuration;
    }

    public Expression getMapExpression() {
        return configuration.getMap();
    }

    public boolean isInheritModel() {
        return configuration.isInheritModel();
    }

    public boolean isIgnoreMissing() {
        return configuration.isIgnoreMissing();
    }

    public Expression getResourceExpression () {
        return configuration.getInclude();
    }
}
