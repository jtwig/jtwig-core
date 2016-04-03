package org.jtwig.model.tree;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;

import java.util.Collection;

public class MacroNode extends Node {
    private final VariableExpression macroName;
    private final Collection<String> macroArgumentNames;
    private final Node content;

    public MacroNode(Position position, VariableExpression macroName, Collection<VariableExpression> macroArgumentNames, Node content) {
        super(position);
        this.macroName = macroName;
        this.macroArgumentNames = Collections2.transform(macroArgumentNames, new Function<VariableExpression, String>() {
            @Override
            public String apply(VariableExpression input) {
                return input.getIdentifier();
            }
        });
        this.content = content;
    }

    public VariableExpression getMacroName() {
        return macroName;
    }

    public Collection<String> getMacroArgumentNames() {
        return macroArgumentNames;
    }

    public Node getContent() {
        return content;
    }
}
