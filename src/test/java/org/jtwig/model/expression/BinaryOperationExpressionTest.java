package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.model.expression.operation.calculators.binary.BinaryOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BinaryOperationExpressionTest {
    private final Position position = mock(Position.class);
    private final Expression leftOperand = mock(Expression.class);
    private final BinaryOperator binaryOperator = BinaryOperator.AND;
    private final Expression rightOperand = mock(Expression.class);
    private final BinaryOperationCalculator binaryOperationCalculator = mock(BinaryOperationCalculator.class);
    private final RenderContext context = mock(RenderContext.class);
    private BinaryOperationExpression underTest = new BinaryOperationExpression(position, leftOperand, binaryOperator, rightOperand);

    @Test
    public void calculate() throws Exception {
        JtwigValue value = mock(JtwigValue.class);
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.create(true));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.create(true));
        when(binaryOperationCalculator.calculate(context, position, leftOperand, rightOperand)).thenReturn(value);

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asBoolean(), is(true));
    }
}