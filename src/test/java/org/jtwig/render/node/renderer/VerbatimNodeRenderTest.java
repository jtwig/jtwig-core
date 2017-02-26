package org.jtwig.render.node.renderer;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.model.tree.VerbatimNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
import org.jtwig.support.MatcherUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class VerbatimNodeRenderTest {
    private final VerbatimNodeRender underTest = new VerbatimNodeRender();

    @Test
    public void render() throws Exception {
        String content = "content";
        EscapeEngine escapeMode = HtmlEscapeEngine.instance();
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        VerbatimNode verbatimNode = mock(VerbatimNode.class);

        when(verbatimNode.getContent()).thenReturn(content);
        when(request.getRenderContext().getCurrent(EscapeEngine.class)).thenReturn(escapeMode);

        Renderable result = underTest.render(request, verbatimNode);

        assertThat(result, is(MatcherUtils.<Renderable>theSameBean(new StringRenderable(content, escapeMode))));
    }
}