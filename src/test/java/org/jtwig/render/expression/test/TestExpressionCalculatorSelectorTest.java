package org.jtwig.render.expression.test;

import com.google.common.base.Optional;
import org.jtwig.model.expression.test.TestExpression;
import org.jtwig.render.expression.test.calculator.TestExpressionCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class TestExpressionCalculatorSelectorTest {
    private final HashMap<Class<? extends TestExpression>, TestExpressionCalculator> calculatorMap = new HashMap<>();
    private TestExpressionCalculatorSelector underTest = new TestExpressionCalculatorSelector(calculatorMap);

    @Before
    public void setUp() throws Exception {
        calculatorMap.clear();
    }

    @Test
    public void calculatorForNotPresent() throws Exception {
        TestExpression testExpression = mock(TestExpression.class);

        Optional<TestExpressionCalculator> result = underTest.calculatorFor(testExpression);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void calculatorForPresent() throws Exception {
        TestExpression testExpression = mock(TestExpression.class);
        TestExpressionCalculator testExpressionCalculator = mock(TestExpressionCalculator.class);

        calculatorMap.put(testExpression.getClass(), testExpressionCalculator);

        Optional<TestExpressionCalculator> result = underTest.calculatorFor(testExpression);

        assertSame(testExpressionCalculator, result.get());
    }
}