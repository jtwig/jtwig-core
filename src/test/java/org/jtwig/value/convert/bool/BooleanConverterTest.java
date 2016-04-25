package org.jtwig.value.convert.bool;

import com.google.common.collect.ImmutableMap;
import org.jtwig.value.Undefined;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static java.util.Collections.*;
import static org.junit.Assert.assertEquals;

public class BooleanConverterTest {
    private BooleanConverter underTest = new BooleanConverter();

    @Test
    public void convertNull() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(null);

        assertEquals(false, result.get());
    }

    @Test
    public void convertUndefined() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(Undefined.UNDEFINED);

        assertEquals(false, result.get());
    }

    @Test
    public void convertStringTrue() throws Exception {
        Converter.Result<Boolean> result = underTest.convert("true");

        assertEquals(true, result.get());
    }

    @Test
    public void convertStringFalse() throws Exception {
        Converter.Result<Boolean> result = underTest.convert("false");

        assertEquals(false, result.get());
    }

    @Test
    public void convertStringEmpty() throws Exception {
        Converter.Result<Boolean> result = underTest.convert("");

        assertEquals(false, result.isDefined());
    }

    @Test
    public void convertStringNotEmpty() throws Exception {
        Converter.Result<Boolean> result = underTest.convert("asdsad");

        assertEquals(false, result.isDefined());
    }

    @Test
    public void convertArrayEmpty() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(new Object[]{});

        assertEquals(false, result.get());
    }

    @Test
    public void convertArrayNonEmpty() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(new Object[]{new Object()});

        assertEquals(true, result.get());
    }

    @Test
    public void convertEmptyList() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(emptyList());

        assertEquals(false, result.get());
    }

    @Test
    public void convertEmptyMap() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(emptyMap());

        assertEquals(false, result.get());
    }

    @Test
    public void convertMap() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(ImmutableMap.of("String", "asd"));

        assertEquals(true, result.get());
    }

    @Test
    public void convertNonEmptyList() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(singletonList(new Object()));

        assertEquals(true, result.get());
    }

    @Test
    public void convertNumberZero() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(0);

        assertEquals(false, result.get());
    }

    @Test
    public void convertNumberNonZero() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(1);

        assertEquals(true, result.get());
    }

    @Test
    public void convertOther() throws Exception {
        Converter.Result<Boolean> result = underTest.convert(new Object());

        assertEquals(false, result.isDefined());
    }
}