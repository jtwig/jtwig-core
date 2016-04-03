package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;

import java.util.Collection;

public class EmbedNode extends Node {
    private final Collection<OverrideBlockNode> nodes;
    private final IncludeConfiguration includeConfiguration;

    public EmbedNode(Position position, Collection<OverrideBlockNode> nodes, IncludeConfiguration includeConfiguration) {
        super(position);
        this.nodes = nodes;
        this.includeConfiguration = includeConfiguration;
    }

    public Collection<OverrideBlockNode> getNodes() {
        return nodes;
    }

    public Expression getResourceExpression () { return includeConfiguration.getInclude(); }

    public Expression getMapExpression() {
        return includeConfiguration.getMap();
    }

    public boolean isInheritModel() {
        return includeConfiguration.isInheritModel();
    }

    public boolean isIgnoreMissing() {
        return includeConfiguration.isIgnoreMissing();
    }
}
