package org.jtwig.model.expression.operation.calculators.binary;

import com.google.common.base.Optional;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.property.PropertyResolver;
import org.jtwig.util.JtwigValue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SelectionOperationCalculatorTest {
    private final RenderContext renderContext = mock(RenderContext.class);
    private final Position position = mock(Position.class);
    private final Expression leftOperand = mock(Expression.class);
    private final Configuration configuration = mock(Configuration.class);
    private final PropertyResolver propertyResolver = mock(PropertyResolver.class);
    private SelectionOperationCalculator underTest = new SelectionOperationCalculator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void calculateWhenRightElementNotVariableNorFunction() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Expecting variable or function, but got ConstantExpression"));

        underTest.calculate(renderContext, position, leftOperand, new ConstantExpression(position, "hello"));
    }

    @Test
    public void calculateWhenRightElementVariable() throws Exception {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.configuration()).thenReturn(configuration);
        when(configuration.propertyResolver()).thenReturn(propertyResolver);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.of(jtwigValue));
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        VariableExpression rightOperand = new VariableExpression(position, "test");

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertSame(result, jtwigValue);
    }

    @Test
    public void calculateWhenRightElementFunction() throws Exception {
        JtwigValue jtwigValue = mock(JtwigValue.class);
        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.configuration()).thenReturn(configuration);
        when(configuration.propertyResolver()).thenReturn(propertyResolver);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.of(jtwigValue));
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        Expression rightOperand = new FunctionExpression(position, "test", new ArrayList<Argument>());

        JtwigValue result = underTest.calculate(renderContext, position, leftOperand, rightOperand);

        assertSame(result, jtwigValue);
    }

    @Test
    public void calculateWhenItFailsToResolve() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Cannot resolve property 'test'"));

        JtwigValue leftOperandValue = mock(JtwigValue.class);
        when(renderContext.configuration()).thenReturn(configuration);
        when(configuration.propertyResolver()).thenReturn(propertyResolver);
        when(propertyResolver.resolve(any(PropertyResolveRequest.class))).thenReturn(Optional.<JtwigValue>absent());
        when(leftOperand.calculate(renderContext)).thenReturn(leftOperandValue);
        Expression rightOperand = new FunctionExpression(position, "test", new ArrayList<Argument>());

        underTest.calculate(renderContext, position, leftOperand, rightOperand);
    }
}