package org.jtwig.render.context.model;

import org.jtwig.model.tree.Node;
import org.jtwig.resource.reference.ResourceReference;

public class BlockDefinition {
    private final Node node;
    private final ResourceReference origin;

    public BlockDefinition(Node node, ResourceReference origin) {
        this.node = node;
        this.origin = origin;
    }

    public Node getNode() {
        return node;
    }

    public ResourceReference getSource() {
        return origin;
    }
}
