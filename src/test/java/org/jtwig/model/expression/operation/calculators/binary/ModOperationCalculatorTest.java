package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operation.binary.calculators.ModOperationCalculator;
import org.jtwig.model.position.Position;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.DefaultValueConfiguration;
import org.jtwig.value.environment.ValueEnvironment;
import org.jtwig.value.environment.ValueEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ModOperationCalculatorTest {

    public static final ValueEnvironment VALUE_ENVIRONMENT = new ValueEnvironmentFactory().crete(new DefaultValueConfiguration());
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Position position = mock(Position.class);
    private ModOperationCalculator underTest = new ModOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(renderContext.environment().value().getMathContext()).thenReturn(MathContext.DECIMAL32);
        when(renderContext.environment().value()).thenReturn(VALUE_ENVIRONMENT);
    }

    @Test
    public void mod() throws Exception {
        Expression leftOperand = mock(Expression.class);
        Expression rightOperand = mock(Expression.class);
        when(leftOperand.calculate(renderContext)).thenReturn(JtwigValueFactory.value(new BigDecimal("11"), VALUE_ENVIRONMENT));
        when(rightOperand.calculate(renderContext)).thenReturn(JtwigValueFactory.value(new BigDecimal("2"), VALUE_ENVIRONMENT));

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertThat(result.mandatoryNumber(), is(new BigDecimal(1)));
    }
}