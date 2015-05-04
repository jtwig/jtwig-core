package org.jtwig.functions.impl;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class NumberFunctionsTest {
    private NumberFunctions underTest = new NumberFunctions();

    @Test
    public void numberFormat() throws Exception {
        assertEquals("1,234.57", underTest.numberFormat(1234.5678, 2, ".", ","));
    }
    @Test
    public void numberFormatWithoutGrouping() throws Exception {
        assertEquals("1234.57", underTest.numberFormat(1234.5678, 2, "."));
    }

    @Test
    public void numberFormatWithoutDecimalSeparator() throws Exception {
        assertEquals("1234.57", underTest.numberFormat(1234.5678, 2));
    }
    @Test
    public void numberFormatDefault() throws Exception {
        assertEquals("1234.568", underTest.numberFormat(1234.5678));
    }


    @Test
    public void range() throws Exception {
        List<Integer> list = underTest.range(1, 10, 2);
        assertThat(list, contains(1, 3, 5, 7, 9));
    }
    @Test
    public void rangeDefault() throws Exception {
        List<Integer> list = underTest.range(1, 3);
        assertThat(list, contains(1, 2, 3));
    }
    @Test
    public void rangeChars() throws Exception {
        List<Character> list = underTest.range('A', 'D');
        assertThat(list, contains('A', 'B', 'C', 'D'));
    }

    @Test(expected = Exception.class)
    public void rangeInvalidStep() throws Exception {
        underTest.range(1, 3, 0);
    }
    @Test
    public void rangeHugeStep() throws Exception {
        List<Integer> list = underTest.range(1, 3, 50);
        assertThat(list, contains(1));
    }
    @Test
    public void rangeNegativeStepWithPositiveProgression() throws Exception {
        List<Integer> list = underTest.range(1, 3, -1);
        assertThat(list, contains(1, 2, 3));
    }
}