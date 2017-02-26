package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.ContentEscapeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.RenderException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ContentEscapeNodeRenderTest {
    ContentEscapeNodeRender underTest = new ContentEscapeNodeRender();


    @Test(expected = RenderException.class)
    public void contentEscape() throws Exception {
        RenderRequest request = mock(RenderRequest.class, Mockito.RETURNS_DEEP_STUBS);
        ContentEscapeNode contentEscapeNode = mock(ContentEscapeNode.class);

        given(request.getEnvironment().getEscapeEnvironment().getDefaultEscapeEngine()).willReturn("default");
        given(contentEscapeNode.getEscapeEngineName()).willReturn(Optional.of("escape"));
        given(request.getEnvironment().getEscapeEnvironment().getEscapeEngineSelector().escapeEngineFor("escape")).willReturn(Optional.<EscapeEngine>absent());

        underTest.render(request, contentEscapeNode);
    }
}