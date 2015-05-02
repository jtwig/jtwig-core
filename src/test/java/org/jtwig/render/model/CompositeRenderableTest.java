package org.jtwig.render.model;

import org.jtwig.render.Renderable;
import org.junit.Test;

import java.io.OutputStream;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CompositeRenderableTest {

    private final OutputStream outputStream = mock(OutputStream.class);
    private CompositeRenderable underTest;

    @Test
    public void accept() throws Exception {
        Renderable renderable1 = mock(Renderable.class);
        Renderable renderable2 = mock(Renderable.class);

        underTest = new CompositeRenderable(asList(renderable1, renderable2));
        underTest.accept(outputStream);

        verify(renderable1).accept(outputStream);
        verify(renderable2).accept(outputStream);
    }
}