package org.jtwig.value.convert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullConverterTest {
    private NullConverter underTest = new NullConverter();

    @Test
    public void convertNull() throws Exception {
        Converter.Result result = underTest.convert(null);

        assertEquals(null, result.get());
        assertEquals(true, result.isDefined());
    }

    @Test
    public void convertNotNull() throws Exception {
        Converter.Result result = underTest.convert(new Object());

        assertEquals(false, result.isDefined());
    }
}