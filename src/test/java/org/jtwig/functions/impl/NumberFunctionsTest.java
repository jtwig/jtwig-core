package org.jtwig.functions.impl;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class NumberFunctionsTest {
    private NumberFunctions underTest = new NumberFunctions();

    @Test
    public void numberFormat() throws Exception {
        assertEquals("1,234.57", underTest.numberFormat(1234.5678, new BigDecimal(2), ".", ","));
    }
    @Test
    public void numberFormatWithoutGrouping() throws Exception {
        assertEquals("1234.57", underTest.numberFormat(1234.5678, new BigDecimal(2), "."));
    }

    @Test
    public void numberFormatWithoutDecimalSeparator() throws Exception {
        assertEquals("1234.57", underTest.numberFormat(1234.5678, new BigDecimal(2)));
    }
    @Test
    public void numberFormatDefault() throws Exception {
        assertEquals("1234.568", underTest.numberFormat(1234.5678));
    }
}