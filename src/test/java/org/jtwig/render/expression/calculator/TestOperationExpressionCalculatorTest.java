package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.TestOperationExpression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TestOperationExpressionCalculatorTest {
    private TestOperationExpressionCalculator underTest = new TestOperationExpressionCalculator();

    @Test
    public void calculate() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        TestOperationExpression testOperationExpression = mock(TestOperationExpression.class);
        Position position = mock(Position.class);
        Expression argument = mock(Expression.class);
        TestExpression testExpression = mock(TestExpression.class);
        Object value = new Object();

        when(testOperationExpression.getPosition()).thenReturn(position);
        when(testOperationExpression.getArgument()).thenReturn(argument);
        when(testOperationExpression.getTestExpression()).thenReturn(testExpression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateTestExpressionService().calculate(request, position, testExpression, argument)).thenReturn(value);

        Object result = underTest.calculate(request, testOperationExpression);

        assertSame(value, result);
    }
}