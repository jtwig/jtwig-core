package org.jtwig.render.expression.calculator;

import com.google.common.collect.ImmutableMap;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.MapExpression;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MapExpressionCalculatorTest {
    private MapExpressionCalculator underTest = new MapExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        MapExpression expression = mock(MapExpression.class);
        Expression aExpression = mock(Expression.class);
        Expression bExpression = mock(Expression.class);
        Object aValue = new Object();
        Object bValue = new Object();

        when(expression.getExpressions()).thenReturn(ImmutableMap.<String, Expression>builder()
                .put("a", aExpression)
                .put("b", bExpression)
                .build());
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, aExpression)).thenReturn(aValue);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, bExpression)).thenReturn(bValue);

        Object result = underTest.calculate(request, expression);

        assertEquals(ImmutableMap.builder()
                .put("a", aValue)
                .put("b", bValue)
                .build(), result);
    }
}