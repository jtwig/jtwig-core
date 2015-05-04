package org.jtwig.render;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Mockito.*;

public class StreamRenderResultTest {
    private final OutputStream outputStream = mock(OutputStream.class);
    private StreamRenderResult underTest = new StreamRenderResult(outputStream);

    @Test
    public void append() throws Exception {
        String content = "content";

        underTest.append(content);

        verify(outputStream).write(content.getBytes());
    }

    @Test(expected = RenderException.class)
    public void appendWhenException() throws Exception {
        String content = "content";
        doThrow(IOException.class).when(outputStream).write(any(byte[].class));

        underTest.append(content);
    }

    @Test
    public void flush() throws Exception {
        underTest.flush();

        verify(outputStream).flush();
    }

    @Test(expected = RenderException.class)
    public void flushWhenException() throws Exception {
        doThrow(IOException.class).when(outputStream).flush();

        underTest.flush();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void content() throws Exception {
        underTest.content();
    }
}