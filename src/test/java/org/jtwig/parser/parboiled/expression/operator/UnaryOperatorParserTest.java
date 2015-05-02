package org.jtwig.parser.parboiled.expression.operator;

import org.jtwig.model.expression.operation.UnaryOperator;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UnaryOperatorParserTest extends AbstractParserTest {
    private UnaryOperatorParser underTest = context.parser(UnaryOperatorParser.class);

    @Test
    public void negativeOperator() throws Exception {
        ParsingResult<UnaryOperator> result = parse(underTest.UnaryOperator(UnaryOperator.NEGATIVE), "-");

        assertThat(result.matched, is(true));
        UnaryOperator operator = result.valueStack.pop();
        assertThat(operator, is(UnaryOperator.NEGATIVE));
    }
    @Test
    public void notOperator() throws Exception {
        ParsingResult<UnaryOperator> result = parse(underTest.UnaryOperator(UnaryOperator.NOT), "!");

        assertThat(result.matched, is(true));
        UnaryOperator operator = result.valueStack.pop();
        assertThat(operator, is(UnaryOperator.NOT));
    }

    @Test
    public void saturatedTest() throws Exception {
        UnaryOperator[] values = UnaryOperator.values();

        for (UnaryOperator value : values) {
            underTest.UnaryOperator(value);
        }
    }
}