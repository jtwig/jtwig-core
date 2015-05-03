package org.jtwig.context.model;

import com.google.common.base.Optional;
import org.jtwig.context.values.ValueContext;
import org.jtwig.model.tree.Node;

public class NodeContext {
    private final Node node;
    private final ValueContext valueContext;
    private Optional<EscapeMode> escapeMode = Optional.absent();

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

    public Optional<EscapeMode> mode() {
        return escapeMode;
    }

    public NodeContext mode (EscapeMode escapeMode) {
        this.escapeMode = Optional.fromNullable(escapeMode);
        return this;
    }
}
