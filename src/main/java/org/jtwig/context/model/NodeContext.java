package org.jtwig.context.model;

import org.jtwig.context.values.ValueContext;
import org.jtwig.model.tree.Node;

public class NodeContext {
    private final Node node;
    private final ValueContext valueContext;

    public NodeContext(Node node, ValueContext valueContext) {
        this.node = node;
        this.valueContext = valueContext;
    }

    public Node getNode() {
        return node;
    }

    public ValueContext getValueContext() {
        return valueContext;
    }
}
