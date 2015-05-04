package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
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

public class DivideOperationCalculatorTest {
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);

    private DivideOperationCalculator underTest = new DivideOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(context.configuration().mathContext()).thenReturn(MathContext.DECIMAL32);
    }

    @Test
    public void divide() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(new JtwigValue("1.0"));
        when(rightOperand.calculate(context)).thenReturn(new JtwigValue("2.0"));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.mandatoryNumber(), is(new BigDecimal("0.5")));
    }
}