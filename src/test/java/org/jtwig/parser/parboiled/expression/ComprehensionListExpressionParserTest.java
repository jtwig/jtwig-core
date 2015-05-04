package org.jtwig.parser.parboiled.expression;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.jtwig.model.expression.ComprehensionListExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.parser.ParseException;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ComprehensionListExpressionParserTest extends AbstractParserTest {
    private ComprehensionListExpressionParser underTest = context.parser(ComprehensionListExpressionParser.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void happyComprehension() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "[1..2]");

        Expression exp = result.valueStack.pop();
        assertThat(exp, instanceOf(ComprehensionListExpression.class));
    }
    @Test
    public void missingEndBracketComprehension() throws Exception {
        expectedException.expectCause((Matcher)CoreMatchers.instanceOf(ParseException.class));

        parse(underTest.ExpressionRule(), "[1..2");
    }
}