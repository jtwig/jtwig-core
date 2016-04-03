package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.EnumeratedListExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EnumeratedListExpressionCalculatorTest {
    private EnumeratedListExpressionCalculator underTest = new EnumeratedListExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        EnumeratedListExpression enumeratedListExpression = mock(EnumeratedListExpression.class);
        Expression exp1 = mock(Expression.class);
        Expression exp2 = mock(Expression.class);
        Object exp1Value = new Object();
        Object exp2Value = new Object();

        when(enumeratedListExpression.getExpressions()).thenReturn(Arrays.asList(exp1, exp2));
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, exp1)).thenReturn(exp1Value);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, exp2)).thenReturn(exp2Value);

        Object result = underTest.calculate(request, enumeratedListExpression);

        assertEquals(Arrays.asList(exp1Value, exp2Value), result);
    }
}