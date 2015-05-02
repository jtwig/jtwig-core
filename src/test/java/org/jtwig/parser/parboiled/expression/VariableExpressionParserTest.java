package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class VariableExpressionParserTest extends AbstractParserTest {
    private VariableExpressionParser underTest = context.parser(VariableExpressionParser.class);

    @Test
    public void variableExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "two");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(VariableExpression.class)));
        VariableExpression variableExpression = (VariableExpression) expression;
        assertThat(variableExpression.getIdentifier(), equalTo("two"));
        assertThat(variableExpression.getPosition().getLine(), is(1));
        assertThat(variableExpression.getPosition().getColumn(), is(1));
    }
}