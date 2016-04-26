package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class ConstantExpressionParserTest extends AbstractParserTest {
    private ConstantExpressionParser underTest = context.parser(ConstantExpressionParser.class);

    @Test
    public void numberExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "123");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(ConstantExpression.class)));
        ConstantExpression variableExpression = (ConstantExpression) expression;
        assertThat(variableExpression.getConstantValue().toString(), equalTo("123"));
        assertThat(variableExpression.getPosition().getLine(), is(1));
        assertThat(variableExpression.getPosition().getColumn(), is(1));
    }

    @Test
    public void stringExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "'test'");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(ConstantExpression.class)));
        ConstantExpression variableExpression = (ConstantExpression) expression;
        assertThat(variableExpression.getConstantValue().toString(), equalTo("test"));
        assertThat(variableExpression.getPosition().getLine(), is(1));
        assertThat(variableExpression.getPosition().getColumn(), is(1));
    }

    @Test
    public void trueExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "true");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(ConstantExpression.class)));
        ConstantExpression variableExpression = (ConstantExpression) expression;
        assertEquals(variableExpression.getConstantValue(), true);
        assertThat(variableExpression.getPosition().getLine(), is(1));
        assertThat(variableExpression.getPosition().getColumn(), is(1));
    }

    @Test
    public void falseExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "false");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(ConstantExpression.class)));
        ConstantExpression variableExpression = (ConstantExpression) expression;
        assertEquals(variableExpression.getConstantValue(), false);
        assertThat(variableExpression.getPosition().getLine(), is(1));
        assertThat(variableExpression.getPosition().getColumn(), is(1));
    }
}