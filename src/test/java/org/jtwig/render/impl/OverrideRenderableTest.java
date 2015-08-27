package org.jtwig.render.impl;

import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class OverrideRenderableTest {

    private final Renderable defaultContent = mock(Renderable.class);
    private final RenderResult renderResult = mock(RenderResult.class);
    private OverrideRenderable underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new OverrideRenderable(defaultContent);
    }

    @Test
    public void acceptWhenNoOverrideGiven() throws Exception {
        underTest.appendTo(renderResult);

        verify(defaultContent).appendTo(renderResult);
    }

    @Test
    public void acceptWhenOverrideGiven() throws Exception {
        Renderable override = mock(Renderable.class);
        underTest.overrideWith(override);
        underTest.appendTo(renderResult);

        verify(defaultContent, never()).appendTo(renderResult);
        verify(override).appendTo(renderResult);
    }
}