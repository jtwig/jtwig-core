package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.AbstractParserTest;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.parboiled.Parboiled.createParser;

public class LexicParserTest extends AbstractParserTest {
    private LexicParser underTest = createParser(LexicParser.class, context, emptyList());

    @Test
    public void validIdentifier() throws Exception {
        ParsingResult<String> result = parse(underTest.Identifier(), "a123");

        assertThat(result.matched, is(true));
    }

    @Test
    public void invalidIdentifierWithKeywork() throws Exception {
        underTest = createParser(LexicParser.class, context, asList("one"));

        ParsingResult<String> result = parse(underTest.Identifier(), "one");

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
}