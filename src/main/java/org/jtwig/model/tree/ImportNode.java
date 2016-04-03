package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;

public class ImportNode extends Node {
    private final Expression importExpression;
    private final VariableExpression aliasIdentifier;

    public ImportNode(Position position, Expression importExpression, VariableExpression aliasIdentifier) {
        super(position);
        this.importExpression = importExpression;
        this.aliasIdentifier = aliasIdentifier;
    }

    public Expression getImportExpression() {
        return importExpression;
    }

    public VariableExpression getAliasIdentifier() {
        return aliasIdentifier;
    }

}
