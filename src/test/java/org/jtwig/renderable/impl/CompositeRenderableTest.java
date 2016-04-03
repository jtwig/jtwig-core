package org.jtwig.renderable.impl;

import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CompositeRenderableTest {

    private final RenderResult renderResult = mock(RenderResult.class);
    private CompositeRenderable underTest;

    @Test
    public void accept() throws Exception {
        Renderable renderable1 = mock(Renderable.class);
        Renderable renderable2 = mock(Renderable.class);

        underTest = new CompositeRenderable(asList(renderable1, renderable2));
        underTest.appendTo(renderResult);

        verify(renderable1).appendTo(renderResult);
        verify(renderable2).appendTo(renderResult);
    }
}