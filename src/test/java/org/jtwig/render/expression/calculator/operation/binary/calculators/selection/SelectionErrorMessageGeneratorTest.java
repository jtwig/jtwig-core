package org.jtwig.render.expression.calculator.operation.binary.calculators.selection;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class SelectionErrorMessageGeneratorTest {
    private SelectionErrorMessageGenerator underTest = new SelectionErrorMessageGenerator();

    @Test
    public void explainLeftAndRightPresent() throws Exception {
        String left = "left";
        String right = "right";

        VariableExpression leftOperand = mock(VariableExpression.class);
        FunctionExpression rightOperand = mock(FunctionExpression.class);

        given(leftOperand.getIdentifier()).willReturn(left);
        given(rightOperand.getFunctionIdentifier()).willReturn(right);

        String result = underTest.explain(leftOperand, rightOperand);

        assertThat(result, is(String.format("Cannot extract property '%s' out of '%s'", right, left)));
    }

    @Test
    public void explainOnlyRightPresent() throws Exception {
        String right = "right";

        Expression leftOperand = mock(Expression.class);
        FunctionExpression rightOperand = mock(FunctionExpression.class);

        given(rightOperand.getFunctionIdentifier()).willReturn(right);

        String result = underTest.explain(leftOperand, rightOperand);

        assertThat(result, is(String.format("Cannot extract property '%s'", right)));
    }

    @Test
    public void explainOnlyLeftPresent() throws Exception {
        String left = "left";

        ConstantExpression leftOperand = mock(ConstantExpression.class);
        Expression rightOperand = mock(Expression.class);

        given(leftOperand.getConstantValue()).willReturn(left);

        String result = underTest.explain(leftOperand, rightOperand);

        assertThat(result, is(String.format("Cannot extract property out of '%s'", left)));
    }

    @Test
    public void explainNonePresent() throws Exception {
        Expression leftOperand = mock(Expression.class);
        Expression rightOperand = mock(Expression.class);

        String result = underTest.explain(leftOperand, rightOperand);

        assertThat(result, is("Cannot extract property"));
    }
}