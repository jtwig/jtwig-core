package org.jtwig.render.impl;

import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;
import org.junit.Test;

import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FutureRenderableTest {
    private FutureRenderable underTest;
    private RenderResult renderResult = mock(RenderResult.class);

    @Test(expected = IllegalStateException.class)
    public void unpopulatedShouldThrowAnIllegalStateException() throws Exception {
        underTest = new FutureRenderable();

        underTest.appendTo(renderResult);
    }

    @Test
    public void accept() throws Exception {
        Renderable renderable = mock(Renderable.class);
        underTest = new FutureRenderable();
        underTest.complete(renderable);

        underTest.appendTo(renderResult);

        verify(renderable).appendTo(renderResult);
    }

    @Test(expected = IllegalStateException.class)
    public void completeTwice() throws Exception {
        Renderable renderable = mock(Renderable.class);
        underTest = new FutureRenderable();
        underTest.complete(renderable);
        underTest.complete(renderable);
    }
}