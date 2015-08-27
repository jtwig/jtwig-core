package org.jtwig.model.tree.include;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.Node;

import java.util.Collection;

public class IncludeConfiguration {
    private final boolean inheritModel;
    private final boolean ignoreMissing;
    private final Expression include;
    private final Expression map;

    public IncludeConfiguration(Expression include, Expression map, boolean inheritModel, boolean ignoreMissing) {
        this.inheritModel = inheritModel;
        this.ignoreMissing = ignoreMissing;
        this.include = include;
        this.map = map;
    }

    public boolean isInheritModel() {
        return inheritModel;
    }

    public boolean isIgnoreMissing() {
        return ignoreMissing;
    }

    public Expression getInclude() {
        return include;
    }

    public Expression getMap() {
        return map;
    }
}
