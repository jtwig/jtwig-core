package org.jtwig.functions.impl;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ObjectFunctionsTest {
    private ObjectFunctions underTest = new ObjectFunctions();

    @Test
    public void first() throws Exception {
        assertEquals(underTest.first(new String[]{"a","b","c"}), "a");
        assertEquals(underTest.first(null), null);
        assertEquals(underTest.first(true), true);
    }

    @Test
    public void last() throws Exception {
        assertEquals(underTest.last(new String[]{"a","b","c"}), "c");
        assertEquals(underTest.last(null), null);
        assertEquals(underTest.last(true), true);
    }

    @Test
    public void testDefault() throws Exception {
        assertEquals(underTest.defaultFunction(null, "a"), "a");
        assertEquals(underTest.defaultFunction(null, 1), 1);
    }

    @Test
    public void testNonDefault() throws Exception {
        assertEquals(underTest.defaultFunction(1, "a"), 1);
    }


    @Test
    public void testExecuteWithString() throws Exception {
        int length = underTest.length("test");
        assertThat(length, is(4));
    }
}