package org.jtwig.model.expression;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;

public class BinaryOperationExpressionTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void inject() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Invalid expression, expecting a valid injectable expression (binary operator, variable or function)"));

        new BinaryOperationExpression(mock(Position.class), mock(Expression.class), mock(BinaryOperator.class), mock(Expression.class))
                .inject(mock(Expression.class));
    }
}