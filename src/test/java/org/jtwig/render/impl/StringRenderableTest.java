package org.jtwig.render.impl;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.render.RenderResult;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StringRenderableTest {
    public static final String CONTENT = "content";
    private final RenderResult renderResult = mock(RenderResult.class);
    private StringRenderable underTest;

    @Test
    public void acceptWithNoneEscapeMode() throws Exception {
        underTest = new StringRenderable(CONTENT, EscapeMode.NONE);

        underTest.appendTo(renderResult);

        verify(renderResult).append(CONTENT);
    }
}