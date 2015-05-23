package org.jtwig.model.expression.operation.calculators.binary;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.environment.Environment;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.property.PropertyResolver;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SelectionOperationCalculatorTest {
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Position position = mock(Position.class);
    private final Expression leftOperand = mock(Expression.class);
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private final PropertyResolver propertyResolver = mock(PropertyResolver.class);
    private SelectionOperationCalculator underTest = new SelectionOperationCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        when(renderContext.environment().valueConfiguration()).thenReturn(new CompatibleModeValueConfiguration());
    }

    @Test
    public void calculateWhenRightElementNotVariableNorFunction() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Expecting variable or function, but got ConstantExpression"));

        underTest.calculate(renderContext, position, leftOperand, new ConstantExpression(position, "hello"));
    }

    @Test
    public void calculateWhenRightElementVariable() throws Exception {
        Value value = mock(Value.class);
        when(value.getValue()).thenReturn("");
        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.environment()).thenReturn(environment);
        when(environment.propertyResolver()).thenReturn(propertyResolver);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.of(value));
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        VariableExpression rightOperand = new VariableExpression(position, "test");

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertEquals(result.asObject(), "");
    }

    @Test
    public void calculateWhenRightElementFunction() throws Exception {
        Value jtwigValue = mock(Value.class);
        when(jtwigValue.getValue()).thenReturn("");
        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.environment()).thenReturn(environment);
        when(environment.propertyResolver()).thenReturn(propertyResolver);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.of(jtwigValue));
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        Expression rightOperand = new FunctionExpression(position, "test", new ArrayList<Argument>());

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertEquals(result.asObject(), "");
    }

    @Test
    public void calculateWhenItFailsToResolveWhenStrictModeEnabled() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Impossible to access an attribute 'test' on 'null'"));

        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.environment()).thenReturn(environment);
        when(environment.propertyResolver()).thenReturn(propertyResolver);
        when(environment.renderConfiguration().strictMode()).thenReturn(true);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.<Value>absent());
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        Expression rightOperand = new FunctionExpression(position, "test", new ArrayList<Argument>());

        underTest.calculate(renderContext, position, leftOperand, rightOperand);
    }

    @Test
    public void calculateWhenItFailsToResolveWhenStrictModeDisabled() throws Exception {
        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.environment()).thenReturn(environment);
        when(environment.propertyResolver()).thenReturn(propertyResolver);
        when(environment.renderConfiguration().strictMode()).thenReturn(false);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.<Value>absent());
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        Expression rightOperand = new FunctionExpression(position, "test", new ArrayList<Argument>());

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertThat(result.isUndefined(), is(true));
    }
}