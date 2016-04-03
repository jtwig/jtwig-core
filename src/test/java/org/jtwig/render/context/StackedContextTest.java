package org.jtwig.render.context;

import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StackedContextTest {
    private final Stack stack = new Stack();
    private StackedContext underTest = new StackedContext(stack);

    @Before
    public void setUp() throws Exception {
        stack.clear();
    }

    @Test(expected = EmptyStackException.class)
    public void getCurrentNone() throws Exception {
        underTest.getCurrent();
    }

    @Test
    public void getCurrentSome() throws Exception {
        Object current = new Object();
        stack.add(current);

        Object result = underTest.getCurrent();

        assertSame(current, result);
    }

    @Test
    public void hasCurrentNotPresent() throws Exception {
        boolean result = underTest.hasCurrent();
        assertEquals(false, result);
    }

    @Test
    public void hasCurrentPresent() throws Exception {
        Object current = new Object();
        stack.add(current);

        boolean result = underTest.hasCurrent();
        assertEquals(true, result);
    }

    @Test
    public void end() throws Exception {
        Object current = new Object();
        stack.add(current);

        underTest.end();

        assertTrue(stack.isEmpty());
    }

    @Test
    public void start() throws Exception {
        Object context = new Object();

        underTest.start(context);

        assertFalse(stack.isEmpty());
    }

    @Test
    public void set() throws Exception {
        Object current = new Object();
        Object newContext = new Object();
        stack.add(current);

        underTest.set(newContext);

        assertThat(stack.size(), is(1));
        assertThat(stack.peek(), is(newContext));
    }
}