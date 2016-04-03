package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.renderable.Renderable;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class AutoEscapeNodeRenderTest {
    private AutoEscapeNodeRender underTest = new AutoEscapeNodeRender();

    @Test
    public void renderWhenEscapeModeNotProvided() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);
        StackedContext<EscapeMode> stackedContext = mock(StackedContext.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeMode()).thenReturn(Optional.<EscapeMode>absent());
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);
        when(request.getRenderContext().getEscapeModeContext()).thenReturn(stackedContext);

        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(stackedContext).set(EscapeMode.HTML);
    }

    @Test
    public void renderWhenEscapeModeProvided() throws Exception {
        EscapeMode escapeMode = EscapeMode.JAVASCRIPT;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        AutoEscapeNode node = mock(AutoEscapeNode.class);
        Node content = mock(Node.class);
        Renderable renderable = mock(Renderable.class);
        StackedContext<EscapeMode> stackedContext = mock(StackedContext.class);

        when(node.getContent()).thenReturn(content);
        when(node.getEscapeMode()).thenReturn(Optional.of(escapeMode));
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);
        when(request.getRenderContext().getEscapeModeContext()).thenReturn(stackedContext);

        Renderable result = underTest.render(request, node);

        assertSame(renderable, result);
        verify(stackedContext).set(escapeMode);
    }
}