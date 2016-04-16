package org.jtwig.renderable.impl;

import org.jtwig.render.escape.NoneEscapeEngine;
import org.jtwig.renderable.RenderResult;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StringRenderableTest {
    public static final String CONTENT = "content";
    private final RenderResult renderResult = mock(RenderResult.class);
    private StringRenderable underTest;

    @Test
    public void acceptWithNoneEscapeMode() throws Exception {
        underTest = new StringRenderable(CONTENT, NoneEscapeEngine.instance());

        underTest.appendTo(renderResult);

        verify(renderResult).append(CONTENT);
    }
}