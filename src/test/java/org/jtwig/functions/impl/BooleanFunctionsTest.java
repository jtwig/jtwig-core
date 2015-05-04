package org.jtwig.functions.impl;

import org.jtwig.util.Undefined;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class BooleanFunctionsTest {
    private BooleanFunctions underTest = new BooleanFunctions();

    @Test
    public void even() throws Exception {
        assertTrue(underTest.even(2));
    }
    @Test
    public void evenFalse() throws Exception {
        assertFalse(underTest.even(3));
    }

    @Test
    public void odd() throws Exception {
        assertTrue(underTest.odd(3));
    }
    @Test
    public void oddFalse() throws Exception {
        assertFalse(underTest.odd(2));
    }


    @Test
    public void defined() throws Exception {
        assertTrue(underTest.isDefined("value"));
    }

    @Test
    public void notDefined() throws Exception {
        assertFalse(underTest.isDefined(Undefined.UNDEFINED));
    }

    @Test
    public void divisable() throws Exception {
        assertTrue(underTest.isDivisableBy(2, 1));
    }

    @Test
    public void isNull() throws Exception {
        assertTrue(underTest.isNull(null));
    }

    @Test
    public void isIterable() throws Exception {
        assertTrue(underTest.iterable(new ArrayList<>()));
        assertTrue(underTest.iterable(new Object[0]));
        assertTrue(underTest.iterable(new HashMap<>()));
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(underTest.isEmpty(new ArrayList<>()));
        assertTrue(underTest.isEmpty(new HashMap<>()));
        assertTrue(underTest.isEmpty(null));
        assertTrue(underTest.isEmpty(0));
    }

    @Test
    public void someOnNonEmptyIterator() throws Exception {
        assertFalse(underTest.isEmpty(nonEmptyIterator()));
    }

    @Test
    public void emptyOnEmptyIterator() throws Exception {
        assertTrue(underTest.isEmpty(emptyIterator()));
    }

    private Iterable<Object> nonEmptyIterator() {
        return new Iterable<Object>() {
            @Override
            public Iterator iterator() {
                return asList(1).iterator();
            }
        };
    }

    private Iterable<Object> emptyIterator() {
        return new Iterable<Object>() {
            @Override
            public Iterator iterator() {
                return new ArrayList<>().iterator();
            }
        };
    }
}