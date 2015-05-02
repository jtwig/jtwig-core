package org.jtwig.render.model;

import org.jtwig.render.Renderable;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class OverrideRenderableTest {

    private final Renderable defaultContent = mock(Renderable.class);
    private final OutputStream outputStream = mock(OutputStream.class);
    private OverrideRenderable underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new OverrideRenderable(defaultContent);
    }

    @Test
    public void acceptWhenNoOverrideGiven() throws Exception {
        underTest.accept(outputStream);

        verify(defaultContent).accept(outputStream);
    }

    @Test
    public void acceptWhenOverrideGiven() throws Exception {
        Renderable override = mock(Renderable.class);
        underTest.overrideWith(override);
        underTest.accept(outputStream);

        verify(defaultContent, never()).accept(outputStream);
        verify(override).accept(outputStream);
    }
}