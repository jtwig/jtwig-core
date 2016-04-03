package org.jtwig.render.expression.calculator.enumerated;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CharDescendingOrderEnumerationListStrategyTest {
    private CharDescendingOrderEnumerationListStrategy underTest = new CharDescendingOrderEnumerationListStrategy();

    @Test
    public void enumerateWhenLeftNotChar() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(left)).thenReturn(Converter.Result.<Character>undefined());

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateWhenRightNotChar() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(left)).thenReturn(Converter.Result.defined('a'));
        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(right)).thenReturn(Converter.Result.<Character>undefined());

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateWhenAscending() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(left)).thenReturn(Converter.Result.defined('a'));
        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(right)).thenReturn(Converter.Result.defined('c'));

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertFalse(result.isPresent());
    }

    @Test
    public void enumerateWhenDescending() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(left)).thenReturn(Converter.Result.defined('c'));
        when(request.getEnvironment().getValueEnvironment().getCharConverter().convert(right)).thenReturn(Converter.Result.defined('a'));

        Optional<List<Object>> result = underTest.enumerate(request, left, right);

        assertTrue(result.isPresent());
        assertEquals(asList('c', 'b', 'a'), result.get());
    }
}