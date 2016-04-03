package org.jtwig.render.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.render.expression.calculator.ExpressionCalculator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class ExpressionCalculatorSelectorTest {
    private final HashMap<Class<? extends Expression>, ExpressionCalculator> map = new HashMap<>();
    private ExpressionCalculatorSelector underTest = new ExpressionCalculatorSelector(map);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        map.clear();
    }

    @Test
    public void calculatorForWhenNotPresent() throws Exception {
        Expression expression = mock(Expression.class);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(containsString(String.format("No calculator for expression of type %s", expression.getClass())));

        underTest.calculatorFor(expression);
    }

    @Test
    public void calculatorForWhenPresent() throws Exception {
        Expression expression = mock(Expression.class);
        ExpressionCalculator expressionCalculator = mock(ExpressionCalculator.class);

        map.put(expression.getClass(), expressionCalculator);

        ExpressionCalculator result = underTest.calculatorFor(expression);

        assertSame(expressionCalculator, result);
    }
}