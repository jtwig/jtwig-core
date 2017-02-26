package org.jtwig.model.tree.visitor;

import org.jtwig.model.tree.Node;

public interface NodeVisitor {
    void consume(Node node);
}
