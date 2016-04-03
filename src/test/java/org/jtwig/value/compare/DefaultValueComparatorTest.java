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
        Object left = "testA";
        Object right = "testB";

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }

    @Test
    public void otherThanNumbersGreater() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = "testC";
        Object right = "testB";

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        int result = underTest.compare(request, left, right);

        assertEquals(1, result);
    }

    @Test
    public void otherThanNumbersEqual() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = "test";
        Object right = "test";

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        int result = underTest.compare(request, left, right);

        assertEquals(0, result);
    }

    @Test
    public void compareLeftNumberRightNot() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = "a";
        Object right = "b";

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.TEN));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }

    @Test
    public void compareLeftNumberRightNotNull() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = "a";
        Object right = null;

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.TEN));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        int result = underTest.compare(request, left, right);

        assertEquals(1, result);
    }

    @Test
    public void compareLeftNotRightNumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = "a";
        Object right = "b";

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.TEN));

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }

    @Test
    public void compareLeftNotRightNumberNull() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = null;
        Object right = "a";

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.TEN));

        int result = underTest.compare(request, left, right);

        assertEquals(-1, result);
    }
}