package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.config.ParserConfigurationBuilder;
import org.jtwig.parser.parboiled.ParserContext;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Rule;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.parboiled.Parboiled.createParser;

public class LexicParserTest {
    private final ParserContext context = new ParserContext(ParserConfigurationBuilder.parserConfiguration().build());
    private LexicParser underTest = createParser(LexicParser.class, context);

    @Test
    public void validInteger() throws Exception {
        ParsingResult<String> result = parse(underTest.Number(), "12345");

        assertThat(result.matched, is(true));
    }

    @Test
    public void validFloat() throws Exception {
        ParsingResult<String> result = parse(underTest.Number(), "0.123");

        assertThat(result.matched, is(true));
    }

    @Test
    public void invalidNumber() throws Exception {
        ParsingResult<String> result = parse(underTest.Number(), "1a23");

        assertThat(result.matched, is(true));
    }

    @Test
    public void validIdentifier() throws Exception {
        ParsingResult<String> result = parse(underTest.Identifier(), "a123");

        assertThat(result.matched, is(true));
    }

    @Test
    public void invalidIdentifierWithKeywork() throws Exception {
        underTest = createParser(LexicParser.class, context);

        ParsingResult<String> result = parse(underTest.Identifier(), "if");

        assertThat(result.matched, is(false));
    }

    @Test
    public void validIdentifier_1() throws Exception {
        ParsingResult<String> result = parse(underTest.Identifier(), "abc");

        assertThat(result.matched, is(true));
    }

    @Test
    public void invalidIdentifier() throws Exception {
        ParsingResult<String> result = parse(underTest.Identifier(), "12asd");

        assertThat(result.matched, is(false));
    }

    @Test
    public void invalidIdentifier_1() throws Exception {
        ParsingResult<String> result = parse(underTest.Identifier(), " _a");

        assertThat(result.matched, is(false));
    }

    @Test
    public void validString() throws Exception {
        ParsingResult<String> result = parse(underTest.String(), "'one'");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.pop(), is("one"));
    }

    @Test
    public void invalidString() throws Exception {
        ParsingResult<String> result = parse(underTest.String(), "'o\\'");

        assertThat(result.matched, is(false));
    }

    private ParsingResult<String> parse(Rule rule, String input) {
        return new ReportingParseRunner<String>(rule).run(input);
    }
}