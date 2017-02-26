package org.jtwig.render.context;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RenderContextTest {
    @Test(expected = IllegalStateException.class)
    public void endNoCurrent() throws Exception {
        RenderContext.create().end(String.class);
    }

    @Test(expected = IllegalStateException.class)
    public void getCurrentNoCurrent() throws Exception {
        RenderContext.create().getCurrent(String.class);
    }

    @Test
    public void hasCurrentNoKey() throws Exception {
        assertFalse(RenderContext.create().hasCurrent(String.class));
    }

    @Test
    public void hasCurrentNoValue() throws Exception {
        RenderContext context = RenderContext.create();
        context.start(String.class, "asd").end(String.class);
        assertFalse(context.hasCurrent(String.class));
    }

    @Test
    public void hasCurrentValue() throws Exception {
        RenderContext context = RenderContext.create();
        context.start(String.class, "asd");
        assertTrue(context.hasCurrent(String.class));
    }

    @Test
    public void endOfCurrent() throws Exception {
        RenderContext context = RenderContext.create();
        Runnable runnable = mock(Runnable.class);

        context.start(String.class, "test");
        context.onEndCurrent(String.class, runnable);
        context.end(String.class);

        verify(runnable).run();
    }
}