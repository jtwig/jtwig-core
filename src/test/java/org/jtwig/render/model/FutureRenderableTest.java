package org.jtwig.render.model;

import org.jtwig.render.Renderable;
import org.junit.Test;

import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FutureRenderableTest {
    private FutureRenderable underTest;
    private OutputStream outputStream = mock(OutputStream.class);

    @Test(expected = IllegalStateException.class)
    public void unpopulatedShouldThrowAnIllegalStateException() throws Exception {
        underTest = new FutureRenderable();

        underTest.accept(outputStream);
    }

    @Test
    public void accept() throws Exception {
        Renderable renderable = mock(Renderable.class);
        underTest = new FutureRenderable();
        underTest.complete(renderable);

        underTest.accept(outputStream);

        verify(renderable).accept(outputStream);
    }

    @Test(expected = IllegalStateException.class)
    public void completeTwice() throws Exception {
        Renderable renderable = mock(Renderable.class);
        underTest = new FutureRenderable();
        underTest.complete(renderable);
        underTest.complete(renderable);
    }
}