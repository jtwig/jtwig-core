package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public abstract class AbstractNodeTest {
    private RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);

    @Before
    public void setUp() throws Exception {
        when(renderContext.escapeContext().currentEscapeMode()).thenReturn(EscapeMode.NONE);
    }

    protected String renderResult (Renderable renderable) {
        RenderResult renderResult = new RenderResult();
        renderable.appendTo(renderResult);
        return renderResult.toString();
    }

    protected void render(Node node, String content) {
        when(renderContext.nodeRenderer().render(node)).thenReturn(new StringRenderable(content, EscapeMode.NONE));
    }

    public RenderContext renderContext() {
        return renderContext;
    }
}
