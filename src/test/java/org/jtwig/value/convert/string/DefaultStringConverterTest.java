package org.jtwig.value.convert.string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultStringConverterTest {
    private DefaultStringConverter underTest = new DefaultStringConverter();

    @Test
    public void convert() throws Exception {
        String result = underTest.convert(null);

        assertEquals("", result);
    }

    @Test
    public void convertString() throws Exception {
        Object input = new Object();

        String result = underTest.convert(input);

        assertEquals(input.toString(), result);
    }
}