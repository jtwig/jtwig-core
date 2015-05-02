package org.jtwig.context.model;

import org.jtwig.model.tree.Node;

import java.util.Collection;

public class Macro {
    private final Collection<String> argumentNames;
    private final Node content;

    public Macro(Collection<String> argumentNames, Node content) {
        this.argumentNames = argumentNames;
        this.content = content;
    }

    public Collection<String> getArgumentNames() {
        return argumentNames;
    }

    public Node getContent() {
        return content;
    }
}
