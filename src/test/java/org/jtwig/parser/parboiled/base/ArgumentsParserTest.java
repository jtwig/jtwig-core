package org.jtwig.parser.parboiled.base;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.parser.parboiled.expression.FunctionExpressionParser;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArgumentsParserTest extends AbstractParserTest {
    private FunctionExpressionParser.ArgumentsParser underTest = context.parser(FunctionExpressionParser.ArgumentsParser.class);

    @Test
    public void noArguments() throws Exception {
        ParsingResult<Collection<Expression>> result = parse(underTest.Arguments(), "()");

        assertThat(result.matched, is(true));
    }

    @Test
    public void oneArgument() throws Exception {
        ParsingResult<Collection<Expression>> result = parse(underTest.Arguments(), "(test)");

        assertThat(result.matched, is(true));
    }
}