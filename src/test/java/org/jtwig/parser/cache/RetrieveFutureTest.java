package org.jtwig.parser.cache;

import org.jtwig.renderable.RenderException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrieveFutureTest {
    private RetrieveFuture<Object> underTest = new RetrieveFuture<>();

    @Test(expected = RenderException.class)
    public void applyInterruptedException() throws Exception {
        Future input = mock(Future.class);

        when(input.get()).thenThrow(InterruptedException.class);

        underTest.apply(input);
    }

    @Test(expected = RenderException.class)
    public void applyExecutionException() throws Exception {
        Future input = mock(Future.class);

        when(input.get()).thenThrow(ExecutionException.class);

        underTest.apply(input);
    }
}