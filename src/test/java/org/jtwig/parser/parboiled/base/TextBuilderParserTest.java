package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.node.TextNodeParser;
import org.junit.Test;
import org.parboiled.Rule;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.parboiled.Parboiled.createParser;

public class TextBuilderParserTest extends AbstractParserTest {
    private TextNodeParser.TextBuilderParser underTest = context.parser(TextNodeParser.TextBuilderParser.class);

    @Test
    public void textWithOutput() throws Exception {
        ParsingResult<StringBuilder> result = parse(underTest.Text(), "fghjkhgfdfghj aksdhashd jad gasjhdgjhagdwq {{ asdsad ");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.pop().toString(), is("fghjkhgfdfghj aksdhashd jad gasjhdgjhagdwq "));
    }

    @Test
    public void textWithCode() throws Exception {
        ParsingResult<StringBuilder> result = parse(underTest.Text(), "fghjkhgfdfghj aksdhashd jad gasjhdgjhagdwq {% asdsad ");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.pop().toString(), is("fghjkhgfdfghj aksdhashd jad gasjhdgjhagdwq "));
    }

    @Test
    public void textWithComment() throws Exception {
        ParsingResult<StringBuilder> result = parse(underTest.Text(), "fghjkhgfdfghj aksdhashd jad gasjhdgjhagdwq {# asdsad ");

        assertThat(result.matched, is(true));
        assertThat(result.valueStack.pop().toString(), is("fghjkhgfdfghj aksdhashd jad gasjhdgjhagdwq "));
    }
}