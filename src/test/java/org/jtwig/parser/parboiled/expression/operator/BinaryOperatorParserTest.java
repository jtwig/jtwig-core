package org.jtwig.parser.parboiled.expression.operator;

import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.expression.operator.BinaryOperatorParser;
import org.junit.Test;
import org.parboiled.Rule;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.parboiled.Parboiled.createParser;

public class BinaryOperatorParserTest extends AbstractParserTest {
    private BinaryOperatorParser underTest = context.parser(BinaryOperatorParser.class);

    @Test
    public void andOperator() throws Exception {
        ParsingResult<BinaryOperator> result = parse(underTest.BinaryOperator(BinaryOperator.AND), "and");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.pop(), is(BinaryOperator.AND));
    }

    @Test
    public void divideOperator() throws Exception {
        ParsingResult<BinaryOperator> result = parse(underTest.BinaryOperator(BinaryOperator.DIVIDE), "/");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.pop(), is(BinaryOperator.DIVIDE));
    }

    @Test
    public void invalidDivideOperator() throws Exception {
        ParsingResult<BinaryOperator> result = parse(underTest.BinaryOperator(BinaryOperator.SUM), "/");

        assertThat(result.matched, is(false));
    }

    @Test
    public void saturatedTest() throws Exception {
        BinaryOperator[] values = BinaryOperator.values();

        for (BinaryOperator value : values) {
            underTest.BinaryOperator(value);
        }
    }
}