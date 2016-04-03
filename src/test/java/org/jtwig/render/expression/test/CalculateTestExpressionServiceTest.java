package org.jtwig.render.expression.test;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.test.calculator.TestExpressionCalculator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculateTestExpressionServiceTest {
    private final TestExpressionCalculatorSelector testExpressionCalculatorSelector = mock(TestExpressionCalculatorSelector.class);
    private CalculateTestExpressionService underTest = new CalculateTestExpressionService(testExpressionCalculatorSelector);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateIfNotFound() throws Exception {
        RenderRequest request = mock(RenderRequest.class);
        Position position = mock(Position.class);
        TestExpression testExpression = mock(TestExpression.class);
        Expression expression = mock(Expression.class);

        when(testExpressionCalculatorSelector.calculatorFor(testExpression)).thenReturn(Optional.<TestExpressionCalculator>absent());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(containsString(String.format("Cannot find test calculator for '%s'", testExpression.getClass())));

        underTest.calculate(request, position, testExpression, expression);
    }

    @Test
    public void calculateIfFound() throws Exception {
        RenderRequest request = mock(RenderRequest.class);
        Position position = mock(Position.class);
        TestExpression testExpression = mock(TestExpression.class);
        Expression argument = mock(Expression.class);
        TestExpressionCalculator testExpressionCalculator = mock(TestExpressionCalculator.class);
        Object expected = new Object();

        when(testExpressionCalculatorSelector.calculatorFor(testExpression)).thenReturn(Optional.of(testExpressionCalculator));
        when(testExpressionCalculator.calculate(request, position, testExpression, argument)).thenReturn(expected);

        Object result = underTest.calculate(request, position, testExpression, argument);

        assertSame(expected, result);
    }
}