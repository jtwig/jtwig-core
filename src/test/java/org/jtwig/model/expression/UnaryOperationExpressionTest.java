package org.jtwig.model.expression;

import org.jtwig.configuration.MathContextParameter;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.operation.UnaryOperator;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnaryOperationExpressionTest {
    private final Position position = mock(Position.class);
    private final Expression operand = mock(Expression.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private UnaryOperationExpression underTest = new UnaryOperationExpression(position, UnaryOperator.NEGATIVE, operand);

    @Before
    public void setUp() throws Exception {
        when(context.configuration().parameter(MathContextParameter.mathContext())).thenReturn(MathContext.DECIMAL32);
    }

    @Test
    public void calculate() throws Exception {
        when(operand.calculate(context)).thenReturn(new JtwigValue(1));

        JtwigValue result = underTest.calculate(context);

        assertThat(result.asNumber(), is(new BigDecimal(-1)));
    }
}