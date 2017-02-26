package org.jtwig.render.context;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ContextTest {
    @Test
    public void content() throws Exception {
        Runnable runnable = mock(Runnable.class);
        Context<Object> context = Context.create(new Object());
        context.onEnd(runnable);

        context.end();

        verify(runnable).run();
    }
}