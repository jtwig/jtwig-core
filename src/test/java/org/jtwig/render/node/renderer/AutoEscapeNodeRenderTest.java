package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.escape.EscapeEngine;
import org.jtwig.render.escape.HtmlEscapeEngine;
import org.jtwig.render.escape.JavascriptEscapeEngine;
import org.jtwig.renderable.Renderable;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AutoEscapeNodeRenderTest {
    private AutoEscapeNodeRender underTest = new AutoEscapeNodeRender();

    @Test
    public void renderWhenEscapeModeNotProvided() throws Exception {
        EscapeEngine escapeEngine = HtmlEscapeEngine.instance();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);
        StackedContext<EscapeEngine> stackedContext = mock(StackedContext.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeEngineName()).thenReturn(Optional.<String>absent());
        when(request.getEnvironment().getRenderEnvironment().getEscapeEngineSelector().escapeEngineFor("default")).thenReturn(Optional.of(escapeEngine));
        when(request.getEnvironment().getRenderEnvironment().getDefaultEscapeEngineName()).thenReturn("default");
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);
        when(request.getRenderContext().getEscapeEngineContext()).thenReturn(stackedContext);

        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(stackedContext).set(escapeEngine);
    }

    @Test
    public void renderWhenEscapeModeProvided() throws Exception {
        EscapeEngine escapeMode = JavascriptEscapeEngine.instance();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);
        StackedContext<EscapeEngine> stackedContext = mock(StackedContext.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeEngineName()).thenReturn(Optional.of("js"));
        when(request.getEnvironment().getRenderEnvironment().getEscapeEngineSelector().escapeEngineFor("js")).thenReturn(Optional.of(escapeMode));
        when(request.getEnvironment().getRenderEnvironment().getDefaultEscapeEngineName()).thenReturn("default");
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);
        when(request.getRenderContext().getEscapeEngineContext()).thenReturn(stackedContext);

        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(stackedContext).set(escapeMode);
    }
}