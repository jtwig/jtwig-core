package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.model.LimitProperties;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.parser.config.ParserConfigurationBuilder.parserConfiguration;
import static org.junit.Assert.assertNotNull;
import static org.parboiled.Parboiled.createParser;

public class LimitsParserTest extends AbstractParserTest {
    private final ParserContext parserContext = new ParserContext(parserConfiguration().build());
    private LimitsParser underTest = createParser(LimitsParser.class, parserContext);

    @Test
    public void registered() throws Exception {
        assertNotNull(parserContext.parser(LimitsParser.class));
    }

    @Test
    public void startCommentWithoutDash() throws Exception {
        ParsingResult<LimitProperties> parse = parse(underTest.startComment(), "{#");

        assertThat(parse.matched, is(true));
        assertThat(underTest.lastWhiteSpace(), is(false));
    }

    @Test
    public void startCommentWithDash() throws Exception {
        ParsingResult<LimitProperties> parse = parse(underTest.startComment(), "{#-");

        assertThat(parse.matched, is(true));
        assertThat(underTest.lastWhiteSpace(), is(true));
    }

    @Test
    public void endCommentWithoutDash() throws Exception {
        ParsingResult<LimitProperties> parse = parse(underTest.endComment(), "#}");

        assertThat(parse.matched, is(true));
        assertThat(underTest.lastWhiteSpace(), is(false));
    }

    @Test
    public void endCommentWithDash() throws Exception {
        ParsingResult<LimitProperties> parse = parse(underTest.endComment(), "-#}");

        assertThat(parse.matched, is(true));
        assertThat(underTest.lastWhiteSpace(), is(true));
    }
}