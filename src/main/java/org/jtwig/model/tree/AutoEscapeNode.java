package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.model.position.Position;
import org.jtwig.render.context.model.EscapeMode;

public class AutoEscapeNode extends ContentNode {
    private final Optional<EscapeMode> escapeMode;

    public AutoEscapeNode(Position position, Node content, Optional<EscapeMode> escapeMode) {
        super(position, content);
        this.escapeMode = escapeMode;
    }

    public Optional<EscapeMode> getEscapeMode() {
        return escapeMode;
    }
}
