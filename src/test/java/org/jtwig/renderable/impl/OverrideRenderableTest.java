package org.jtwig.renderable.impl;

import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.Renderable;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

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
        assertThat(underTest.getDefault(), is(defaultContent));
    }
}