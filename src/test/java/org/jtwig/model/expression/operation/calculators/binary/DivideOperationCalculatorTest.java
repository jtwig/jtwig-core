package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.binary.calculators.DivideOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class DivideOperationCalculatorTest {
    private final Position position = mock(Position.class);
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Expression leftOperand = mock(Expression.class);
    private final Expression rightOperand = mock(Expression.class);

    private DivideOperationCalculator underTest = new DivideOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(context.environment().valueConfiguration().getMathContext()).thenReturn(MathContext.DECIMAL32);
        when(context.environment().valueConfiguration()).thenReturn(new DefaultValueConfiguration());
    }

    @Test
    public void divide() throws Exception {
        when(leftOperand.calculate(context)).thenReturn(JtwigValueFactory.value("1.0", new DefaultValueConfiguration()));
        when(rightOperand.calculate(context)).thenReturn(JtwigValueFactory.value("2.0", new DefaultValueConfiguration()));

        JtwigValue result = underTest.calculate(context, position, leftOperand, rightOperand);

        assertThat(result.mandatoryNumber(), is(new BigDecimal("0.5")));
    }
}