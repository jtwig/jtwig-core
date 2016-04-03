package org.jtwig.util;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.context.ValueContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoopCursorTest {
    private final ValueContext parentContext = mock(ValueContext.class);
    private final WrappedCollection collection = mock(WrappedCollection.class);

    private LoopCursor underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new LoopCursor(parentContext, collection);
    }

    @Test
    public void index() throws Exception {
        int result = underTest.getIndex();

        assertEquals(1, result);
    }

    @Test
    public void index0() throws Exception {
        int result = underTest.getIndex0();

        assertEquals(0, result);
    }

    @Test
    public void isFirstTrue() throws Exception {
        boolean result = underTest.isFirst();

        assertEquals(true, result);
    }

    @Test
    public void isFirstFalse() throws Exception {
        underTest.step();

        boolean result = underTest.isFirst();

        assertEquals(false, result);
    }

    @Test
    public void isLastTrue() throws Exception {
        when(collection.size()).thenReturn(0);

        boolean result = underTest.isLast();

        assertEquals(true, result);
    }

    @Test
    public void isLastFalse() throws Exception {
        when(collection.size()).thenReturn(2);

        boolean result = underTest.isLast();

        assertEquals(false, result);
    }

    @Test
    public void revIndex() throws Exception {
        when(collection.size()).thenReturn(2);

        int result = underTest.getRevindex();

        assertEquals(2, result);
    }

    @Test
    public void revIndex0() throws Exception {
        when(collection.size()).thenReturn(2);

        int result = underTest.getRevindex0();

        assertEquals(1, result);
    }
}