package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.model.expression.operation.calculators.binary.BinaryOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BinaryOperationExpressionTest {
    private final Position position = mock(Position.class);
    private final Expression leftOperand = mock(Expression.class);
    private final BinaryOperator binaryOperator = BinaryOperator.AND;
    private final Expression rightOperand = mock(Expression.class);
    private final BinaryOperationCalculator binaryOperationCalculator = mock(BinaryOperationCalculator.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private BinaryOperationExpression underTest = new BinaryOperationExpression(position, leftOperand, binaryOperator, rightOperand);

    @Before
    public void setUp() throws Exception {
        when(context.configuration().valueConfiguration()).thenReturn(NamedValueConfiguration.COMPATIBLE_MODE);
    }

    @Test
    public void calculate() throws Exception {
        JtwigValue value = mock(JtwigValue.class);
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, NamedValueConfiguration.COMPATIBLE_MODE));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value(true, NamedValueConfiguration.COMPATIBLE_MODE));
        when(binaryOperationCalculator.calculate(context, position, leftOperand, rightOperand)).thenReturn(value);

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asBoolean(), is(true));
    }

    @Test(expected = CalculationException.class)
    public void injectWhenLeftNotInjectable() throws Exception {
        underTest.inject(mock(Expression.class));
    }
}