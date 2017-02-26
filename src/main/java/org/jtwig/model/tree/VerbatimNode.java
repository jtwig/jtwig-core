package org.jtwig.model.tree;

import org.jtwig.model.position.Position;

public class VerbatimNode extends Node {
    private final String content;

    public VerbatimNode(Position position, String content) {
        super(position);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
