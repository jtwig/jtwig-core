package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CompositionOperationCalculatorTest {
    private CompositionOperationCalculator underTest = new CompositionOperationCalculator();

    @Test(expected = CalculationException.class)
    public void calculateWhenNotInjectableExpression() throws Exception {
        underTest.calculate(mock(RenderContext.class), mock(Position.class), mock(Expression.class), mock(Expression.class));
    }
}