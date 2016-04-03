package org.jtwig.render.expression.calculator;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.MapSelectionExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class MapSelectionExpressionCalculatorTest {
    private MapSelectionExpressionCalculator underTest = new MapSelectionExpressionCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateWhenNotMap() throws Exception {
        MapSelectionExpression mapSelectionExpression = mock(MapSelectionExpression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        Object mapValue = new Object();

        when(mapSelectionExpression.getMapExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(mapValue);
        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(mapValue)).thenReturn(Converter.Result.<WrappedCollection>undefined());

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString(String.format("Cannot convert %s to a map", mapValue)));

        underTest.calculate(request, mapSelectionExpression);
    }

    @Test
    public void calculateWhenMap() throws Exception {
        MapSelectionExpression mapSelectionExpression = mock(MapSelectionExpression.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        WrappedCollection entries = mock(WrappedCollection.class);
        Expression selectionExpression = mock(Expression.class);
        Expression expression = mock(Expression.class);
        Object selectionValue = new Object();
        Object mapValue = new Object();
        String mapEntryKey = "value";
        Object mapEntryValue = new Object();

        when(mapSelectionExpression.getSelectValue()).thenReturn(selectionExpression);
        when(mapSelectionExpression.getMapExpression()).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(mapValue);
        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(mapValue)).thenReturn(Converter.Result.defined(entries));
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, selectionExpression)).thenReturn(selectionValue);
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(selectionValue)).thenReturn(mapEntryKey);
        when(entries.getValue(mapEntryKey)).thenReturn(mapEntryValue);


        Object result = underTest.calculate(request, mapSelectionExpression);

        assertSame(mapEntryValue, result);
    }
}