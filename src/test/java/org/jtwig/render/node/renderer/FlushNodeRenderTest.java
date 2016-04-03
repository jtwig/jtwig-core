package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.FlushNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.FlushRenderable;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class FlushNodeRenderTest {
    private FlushNodeRender underTest = new FlushNodeRender();

    @Test
    public void render() throws Exception {
        FlushNode flushNode = mock(FlushNode.class);
        RenderRequest request = mock(RenderRequest.class);

        Renderable result = underTest.render(request, flushNode);

        assertSame(FlushRenderable.instance(), result);
    }
}