package org.jtwig.value.convert.character;

import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharConverterTest {
    private CharConverter underTest = new CharConverter();

    @Test
    public void convert() throws Exception {
        Object item = 'a';

        Converter.Result<Character> result = underTest.convert('a');

        assertEquals(item, result.get());
    }

    @Test
    public void convertString() throws Exception {
        Object item = 'a';

        Converter.Result<Character> result = underTest.convert("a");

        assertEquals(item, result.get());
    }
}