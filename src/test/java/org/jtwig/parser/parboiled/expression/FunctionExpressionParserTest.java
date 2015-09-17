package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class FunctionExpressionParserTest extends AbstractParserTest {
    private FunctionExpressionParser underTest = context.parser(FunctionExpressionParser.class);



    @Test
    public void functionExpressionOneUnnamedArgument() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "two(1)");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(FunctionExpression.class)));
        FunctionExpression functionExpression = (FunctionExpression) expression;
        assertThat(functionExpression.getFunctionIdentifier(), equalTo("two"));
        assertThat(functionExpression.getArguments().size(), equalTo(1));
        assertThat(functionExpression.getPosition().getLine(), is(1));
        assertThat(functionExpression.getPosition().getColumn(), is(1));
    }

    @Test
    public void functionExpressionTwoUnnamedArgument() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "two(1,2)");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(FunctionExpression.class)));
        FunctionExpression functionExpression = (FunctionExpression) expression;
        assertThat(functionExpression.getFunctionIdentifier(), equalTo("two"));
        assertThat(functionExpression.getArguments().size(), equalTo(2));
        assertThat(functionExpression.getPosition().getLine(), is(1));
        assertThat(functionExpression.getPosition().getColumn(), is(1));
    }

}