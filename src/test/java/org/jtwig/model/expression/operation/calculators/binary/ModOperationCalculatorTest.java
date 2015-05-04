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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ModOperationCalculatorTest {
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Position position = mock(Position.class);
    private ModOperationCalculator underTest = new ModOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(renderContext.configuration().mathContext()).thenReturn(MathContext.DECIMAL32);
    }

    @Test
    public void mod() throws Exception {
        Expression leftOperand = mock(Expression.class);
        Expression rightOperand = mock(Expression.class);
        when(leftOperand.calculate(renderContext)).thenReturn(new JtwigValue(new BigDecimal("11")));
        when(rightOperand.calculate(renderContext)).thenReturn(new JtwigValue(new BigDecimal("2")));

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertThat(result.mandatoryNumber(), is(new BigDecimal(1)));
    }
}