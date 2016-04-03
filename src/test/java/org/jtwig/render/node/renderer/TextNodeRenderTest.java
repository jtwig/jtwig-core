package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.TextNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
import org.jtwig.support.MatcherUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class TextNodeRenderTest {
    private TextNodeRender underTest = new TextNodeRender();

    @Test
    public void renderNoTrimming() throws Exception {
        String content = " content ";
        EscapeMode escapeMode = EscapeMode.HTML;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        TextNode textNode = mock(TextNode.class, RETURNS_DEEP_STUBS);

        when(textNode.getConfiguration().isTrimLeft()).thenReturn(false);
        when(textNode.getConfiguration().isTrimRight()).thenReturn(false);
        when(textNode.getText()).thenReturn(content);
        when(request.getRenderContext().getEscapeModeContext().getCurrent()).thenReturn(escapeMode);

        Renderable result = underTest.render(request, textNode);

        assertThat(result, is(MatcherUtils.<Renderable>theSameBean(new StringRenderable(content, escapeMode))));
    }

    @Test
    public void renderWithLeftTrim() throws Exception {
        String content = " content ";
        EscapeMode escapeMode = EscapeMode.HTML;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        TextNode textNode = mock(TextNode.class, RETURNS_DEEP_STUBS);

        when(textNode.getConfiguration().isTrimLeft()).thenReturn(true);
        when(textNode.getConfiguration().isTrimRight()).thenReturn(false);
        when(textNode.getText()).thenReturn(content);
        when(request.getRenderContext().getEscapeModeContext().getCurrent()).thenReturn(escapeMode);

        Renderable result = underTest.render(request, textNode);

        assertThat(result, is(MatcherUtils.<Renderable>theSameBean(new StringRenderable("content ", escapeMode))));
    }

    @Test
    public void renderWithRightTrim() throws Exception {
        String content = " content ";
        EscapeMode escapeMode = EscapeMode.HTML;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        TextNode textNode = mock(TextNode.class, RETURNS_DEEP_STUBS);

        when(textNode.getConfiguration().isTrimLeft()).thenReturn(false);
        when(textNode.getConfiguration().isTrimRight()).thenReturn(true);
        when(textNode.getText()).thenReturn(content);
        when(request.getRenderContext().getEscapeModeContext().getCurrent()).thenReturn(escapeMode);

        Renderable result = underTest.render(request, textNode);

        assertThat(result, is(MatcherUtils.<Renderable>theSameBean(new StringRenderable(" content", escapeMode))));
    }

    @Test
    public void renderWithLeftAndRightTrim() throws Exception {
        String content = " content ";
        EscapeMode escapeMode = EscapeMode.HTML;
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        TextNode textNode = mock(TextNode.class, RETURNS_DEEP_STUBS);

        when(textNode.getConfiguration().isTrimLeft()).thenReturn(true);
        when(textNode.getConfiguration().isTrimRight()).thenReturn(true);
        when(textNode.getText()).thenReturn(content);
        when(request.getRenderContext().getEscapeModeContext().getCurrent()).thenReturn(escapeMode);

        Renderable result = underTest.render(request, textNode);

        assertThat(result, is(MatcherUtils.<Renderable>theSameBean(new StringRenderable("content", escapeMode))));
    }
}