package org.jtwig.value.compare;

import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultValueComparatorTest {
    private final DefaultValueComparator underTest = new DefaultValueComparator();

    @Test
    public void compareIfNumbersEqual() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.ONE));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.ONE));

        int result = underTest.compare(request, left, right);

        assertEquals(0, result);
    }

    @Test
    public void compareIfNumbersLess() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.ZERO));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.ONE));

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }

    @Test
    public void compareIfNumbersGreater() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.TEN));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.ONE));

        int result = underTest.compare(request, left, right);

        assertEquals(1, result);
    }

    @Test
    public void otherThanNumbersLess() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(left)).thenReturn("testA");
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(right)).thenReturn("testB");

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }

    @Test
    public void otherThanNumbersGreater() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(left)).thenReturn("testC");
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(right)).thenReturn("testB");

        int result = underTest.compare(request, left, right);

        assertEquals(1, result);
    }

    @Test
    public void otherThanNumbersEqual() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(left)).thenReturn("test");
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(right)).thenReturn("test");

        int result = underTest.compare(request, left, right);

        assertEquals(0, result);
    }

    @Test
    public void compareLeftNumberRightNot() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.TEN));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(left)).thenReturn("a");
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(right)).thenReturn("b");

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }

    @Test
    public void compareLeftNotRightNumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.TEN));
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(left)).thenReturn("a");
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(right)).thenReturn("b");

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }
}