package org.jtwig.render.expression.calculator.operation.binary;

import com.google.common.base.Optional;
import org.jtwig.render.expression.calculator.operation.binary.calculators.BinaryOperationCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class BinaryOperationCalculatorSelectorTest {
    private final HashMap<Class<? extends BinaryOperator>, BinaryOperationCalculator> calculatorMap = new HashMap<>();
    private BinaryOperationCalculatorSelector underTest = new BinaryOperationCalculatorSelector(calculatorMap);

    @Before
    public void setUp() throws Exception {
        calculatorMap.clear();
    }

    @Test
    public void calculatorForWhenNotPresent() throws Exception {
        BinaryOperator operator = mock(BinaryOperator.class);

        Optional<BinaryOperationCalculator> result = underTest.calculatorFor(operator);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void calculatorForWhenPresent() throws Exception {
        BinaryOperator operator = mock(BinaryOperator.class);
        BinaryOperationCalculator binaryOperationCalculator = mock(BinaryOperationCalculator.class);

        calculatorMap.put(operator.getClass(), binaryOperationCalculator);

        Optional<BinaryOperationCalculator> result = underTest.calculatorFor(operator);

        assertThat(result.isPresent(), is(true));
        assertSame(binaryOperationCalculator, result.get());
    }
}