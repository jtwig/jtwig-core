package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.model.position.Position;

public class ContentEscapeNode extends ContentNode {
    private final Optional<String> escapeEngineName;

    public ContentEscapeNode(Position position, Node content, Optional<String> escapeEngineName) {
        super(position, content);
        this.escapeEngineName = escapeEngineName;
    }

    public Optional<String> getEscapeEngineName() {
        return escapeEngineName;
    }
}
