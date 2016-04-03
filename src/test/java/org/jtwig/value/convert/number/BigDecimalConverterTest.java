package org.jtwig.value.convert.number;

import org.jtwig.value.Undefined;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BigDecimalConverterTest {
    private BigDecimalConverter underTest = new BigDecimalConverter();

    @Test
    public void convertNull() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert(null);

        assertEquals(BigDecimal.ZERO, result.get());
    }

    @Test
    public void convertUndefined() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert(Undefined.UNDEFINED);

        assertEquals(BigDecimal.ZERO, result.get());
    }

    @Test
    public void convertBigDecimal() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, result.get());
    }

    @Test
    public void convertInteger() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert(1);

        assertEquals(BigDecimal.ONE, result.get());
    }

    @Test
    public void convertFloat() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert(1.5);

        assertEquals(new BigDecimal("1.5"), result.get());
    }

    @Test
    public void convertString() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert("1123.12");

        assertEquals(new BigDecimal("1123.12"), result.get());
    }

    @Test
    public void convertStringWrong() throws Exception {
        Converter.Result<BigDecimal> result = underTest.convert("123a");

        assertEquals(false, result.isDefined());
    }
}