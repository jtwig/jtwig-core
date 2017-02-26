package org.jtwig.render.node;

import com.google.common.base.Optional;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.renderable.Renderable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RenderNodeServiceTest {
    private final NodeRenderSelector nodeRenderSelector = mock(NodeRenderSelector.class);
    private RenderNodeService underTest = new RenderNodeService(nodeRenderSelector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderNoRenderFound() throws Exception {
        Node node = mock(Node.class);
        RenderRequest renderRequest = mock(RenderRequest.class, RETURNS_DEEP_STUBS);

        when(nodeRenderSelector.renderFor(node)).thenReturn(Optional.<NodeRender>absent());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(containsString("No render found for class org.jtwig.model.tree.Node"));

        underTest.render(renderRequest, node);
    }

    @Test
    public void render() throws Exception {
        EscapeEngine escapeMode = HtmlEscapeEngine.instance();
        Node node = mock(Node.class);
        RenderRequest renderRequest = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        NodeRender nodeRender = mock(NodeRender.class);
        Renderable renderable = mock(Renderable.class);

        when(renderRequest.getRenderContext().getCurrent(EscapeEngine.class)).thenReturn(escapeMode);
        when(nodeRenderSelector.renderFor(node)).thenReturn(Optional.of(nodeRender));
        when(nodeRender.render(renderRequest, node)).thenReturn(renderable);

        Renderable result = underTest.render(renderRequest, node);

        assertSame(renderable, result);
        verify(renderRequest.getRenderContext()).start(EscapeEngine.class, escapeMode);
        verify(renderRequest.getRenderContext()).end(EscapeEngine.class);
    }
}