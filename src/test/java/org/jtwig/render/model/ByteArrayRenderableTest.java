package org.jtwig.render.model;

import org.jtwig.render.RenderException;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ByteArrayRenderableTest {
    public static final byte[] CONTENT = "content".getBytes();
    private final OutputStream outputStream = mock(OutputStream.class);
    private ByteArrayRenderable underTest = new ByteArrayRenderable(CONTENT);

    @Test
    public void accept() throws Exception {
        underTest.accept(outputStream);

        verify(outputStream).write(CONTENT);
    }

    @Test(expected = RenderException.class)
    public void acceptWithIOException() throws Exception {
        doThrow(IOException.class).when(outputStream).write(any(byte[].class));

        underTest.accept(outputStream);

        verify(outputStream).write(CONTENT);
    }
}