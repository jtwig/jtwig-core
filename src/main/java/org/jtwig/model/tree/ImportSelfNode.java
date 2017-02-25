package org.jtwig.model.tree;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;

public class ImportSelfNode extends Node {
    private final VariableExpression aliasIdentifier;

    public ImportSelfNode(Position position, VariableExpression aliasIdentifier) {
        super(position);
        this.aliasIdentifier = aliasIdentifier;
    }

    public VariableExpression getAliasIdentifier() {
        return aliasIdentifier;
    }
}
