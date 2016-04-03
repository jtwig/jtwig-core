package org.jtwig.value.convert.collection;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ArrayToCollectionConverterTest {
    private ArrayToCollectionConverter underTest = new ArrayToCollectionConverter();

    @Test
    public void convertNotArray() throws Exception {
        Converter.Result<WrappedCollection> result = underTest.convert(new Object());

        assertEquals(false, result.isDefined());
    }

    @Test
    public void convertArray() throws Exception {
        Object first = new Object();
        Object last = new Object();

        Converter.Result<WrappedCollection> result = underTest.convert(new Object[] { first, last });

        WrappedCollection entries = result.get();

        assertSame(first, entries.getValue("0"));
        assertSame(last, entries.getValue("1"));
    }
}