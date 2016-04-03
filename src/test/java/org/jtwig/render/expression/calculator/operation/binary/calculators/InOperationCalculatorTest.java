package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOperationCalculatorTest {
    private InOperationCalculator underTest = new InOperationCalculator();

    @Test
    public void calculateIfUndefinedEquals() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(right)).thenReturn(Converter.Result.<WrappedCollection>undefined());
        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, right)).thenReturn(0);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(true, result);
    }

    @Test
    public void calculateIfUndefinedDifferent() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();

        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(right)).thenReturn(Converter.Result.<WrappedCollection>undefined());
        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, right)).thenReturn(1);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(false, result);
    }

    @Test
    public void calculateIfDefinedContains() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();
        Object element = new Object();

        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(right)).thenReturn(Converter.Result.defined(WrappedCollection.singleton(element)));
        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, element)).thenReturn(0);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(true, result);
    }

    @Test
    public void calculateIfDefinedNotContains() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Object left = new Object();
        Object right = new Object();
        Object element = new Object();

        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(right)).thenReturn(Converter.Result.defined(WrappedCollection.singleton(element)));
        when(request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, element)).thenReturn(1);

        Object result = underTest.calculate(request, mock(Position.class), left, right);

        assertEquals(false, result);
    }
}