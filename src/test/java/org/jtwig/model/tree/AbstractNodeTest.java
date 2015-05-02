package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.ByteArrayRenderable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public abstract class AbstractNodeTest {
    private RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);

    protected String renderResult (Renderable renderable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderable.accept(outputStream);
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }

    protected void render(Node node, String content) {
        when(renderContext.nodeRenderer().render(node)).thenReturn(new ByteArrayRenderable(content.getBytes()));
    }

    public RenderContext renderContext() {
        return renderContext;
    }
}
