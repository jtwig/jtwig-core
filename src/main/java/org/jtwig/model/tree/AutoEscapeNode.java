package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;

public class AutoEscapeNode extends ContentNode {
    private final Optional<EscapeMode> escapeMode;

    public AutoEscapeNode(Position position, Node content, Optional<EscapeMode> escapeMode) {
        super(position, content);
        this.escapeMode = escapeMode;
    }

    @Override
    public Renderable render(RenderContext context) {
        context.escapeContext().startEscapeMode(escapeMode.or(EscapeMode.HTML));
        Renderable render = super.render(context);
        context.escapeContext().stop();
        return render;
    }
}
