package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrOperationCalculatorTest {
    private OrOperationCalculator underTest = new OrOperationCalculator();

    @Test
    public void calculate() throws Exception {
        assertThat(underTest.calculate(true, true), is(true));
        assertThat(underTest.calculate(false, true), is(true));
        assertThat(underTest.calculate(true, false), is(true));
        assertThat(underTest.calculate(false, false), is(false));
    }
}