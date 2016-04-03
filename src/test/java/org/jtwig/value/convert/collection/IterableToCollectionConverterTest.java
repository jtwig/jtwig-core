package org.jtwig.value.convert.collection;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class IterableToCollectionConverterTest {
    private IterableToCollectionConverter underTest = new IterableToCollectionConverter();

    @Test
    public void convertNonIterable() throws Exception {
        Object object = new Object();

        Converter.Result<WrappedCollection> result = underTest.convert(object);

        assertEquals(false, result.isDefined());
    }

    @Test
    public void convertIterable() throws Exception {
        Object first = new Object();
        Object second = new Object();
        List<Object> list = asList(first, second);

        Converter.Result<WrappedCollection> result = underTest.convert(list);

        WrappedCollection entries = result.get();

        assertSame(first, entries.getValue("0"));
        assertSame(second, entries.getValue("1"));
    }
}