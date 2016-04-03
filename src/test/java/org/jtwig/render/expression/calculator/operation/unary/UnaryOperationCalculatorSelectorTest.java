package org.jtwig.render.expression.calculator.operation.unary;

import com.google.common.base.Optional;
import org.jtwig.render.expression.calculator.operation.unary.calculators.UnaryOperationCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class UnaryOperationCalculatorSelectorTest {
    private final HashMap<Class<? extends UnaryOperator>, UnaryOperationCalculator> calculatorMap = new HashMap<>();
    private UnaryOperationCalculatorSelector underTest = new UnaryOperationCalculatorSelector(calculatorMap);

    @Before
    public void setUp() throws Exception {
        calculatorMap.clear();
    }

    @Test
    public void calculatorForNotPresent() throws Exception {
        UnaryOperator operator = mock(UnaryOperator.class);

        Optional<UnaryOperationCalculator> result = underTest.calculatorFor(operator);

        assertFalse(result.isPresent());
    }

    @Test
    public void calculatorForPresent() throws Exception {
        UnaryOperator operator = mock(UnaryOperator.class);

        UnaryOperationCalculator unaryOperationCalculator = mock(UnaryOperationCalculator.class);
        calculatorMap.put(operator.getClass(), unaryOperationCalculator);

        Optional<UnaryOperationCalculator> result = underTest.calculatorFor(operator);

        assertTrue(result.isPresent());
        assertSame(unaryOperationCalculator, result.get());
    }
}