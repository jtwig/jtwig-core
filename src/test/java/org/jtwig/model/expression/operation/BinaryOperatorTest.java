package org.jtwig.model.expression.operation;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryOperatorTest {
    @Test
    public void noNullCalculator() throws Exception {
        for (BinaryOperator binaryOperator : BinaryOperator.values()) {
            assertNotNull(binaryOperator.calculator());
        }
    }
}