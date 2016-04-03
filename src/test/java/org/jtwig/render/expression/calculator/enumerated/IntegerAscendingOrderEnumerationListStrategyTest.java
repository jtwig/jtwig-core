package org.jtwig.render.expression.calculator.enumerated;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IntegerAscendingOrderEnumerationListStrategyTest {
    private IntegerAscendingOrderEnumerationListStrategy underTest = new IntegerAscendingOrderEnumerationListStrategy();

    @Test
    public void enumerateLeftNotNumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.<BigDecimal>undefined());

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateRightNotNumber() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.ONE));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.<BigDecimal>undefined());

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateDescending() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.ONE));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.ZERO));

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateAscending() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left)).thenReturn(Converter.Result.defined(BigDecimal.ZERO));
        when(request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right)).thenReturn(Converter.Result.defined(BigDecimal.TEN));

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertTrue(result.isPresent());
        assertEquals(asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), result.get());
    }
}