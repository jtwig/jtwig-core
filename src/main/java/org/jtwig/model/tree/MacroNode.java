package org.jtwig.model.tree;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.Macro;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.EmptyRenderable;

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

    @Override
    public Renderable render(RenderContext context) {
        Macro macro = new Macro(macroArgumentNames, content);
        context.currentResource().register(macroName.getIdentifier(), macro);
        return EmptyRenderable.instance();
    }
}
