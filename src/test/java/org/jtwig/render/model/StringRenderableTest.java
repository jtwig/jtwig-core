package org.jtwig.render.model;

import org.jtwig.context.model.EscapeMode;
import org.jtwig.render.RenderException;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StringRenderableTest {
    public static final String CONTENT = "content";
    private final OutputStream outputStream = mock(OutputStream.class);
    private StringRenderable underTest;

    @Test
    public void acceptWithNoneEscapeMode() throws Exception {
        underTest = new StringRenderable(CONTENT, EscapeMode.NONE);

        underTest.accept(outputStream);

        verify(outputStream).write(CONTENT.getBytes());
    }

    @Test(expected = RenderException.class)
    public void acceptWithIOException() throws Exception {
        underTest = new StringRenderable(CONTENT, EscapeMode.NONE);
        doThrow(IOException.class).when(outputStream).write(any(byte[].class));

        underTest.accept(outputStream);

        verify(outputStream).write(CONTENT.getBytes());
    }
}