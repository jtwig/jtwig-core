package org.jtwig.model.tree;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;

import java.util.ArrayList;
import java.util.List;

public class MacroNode extends ContentNode {
    public static MacroNode create (Position position, VariableExpression macroName, List<VariableExpression> macroArgumentExpressions, Node content) {
        List<String> macroArgumentNames = new ArrayList<>();
        for (VariableExpression variableExpression : macroArgumentExpressions) {
            macroArgumentNames.add(variableExpression.getIdentifier());
        }
        return new MacroNode(position, macroName, macroArgumentNames, content);
    }

    private final VariableExpression macroName;
    private final List<String> macroArgumentNames;

    public MacroNode(Position position, VariableExpression macroName, List<String> macroArgumentNames, Node content) {
        super(position, content);
        this.macroName = macroName;
        this.macroArgumentNames = macroArgumentNames;
    }

    public VariableExpression getMacroName() {
        return macroName;
    }

    public List<String> getMacroArgumentNames() {
        return macroArgumentNames;
    }
}
