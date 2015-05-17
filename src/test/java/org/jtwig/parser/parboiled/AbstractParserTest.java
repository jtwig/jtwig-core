package org.jtwig.parser.parboiled;

import org.jtwig.resource.StringResource;
import org.parboiled.Rule;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

public abstract class AbstractParserTest {
    protected final ParserContext context = ParserContext.instance(new StringResource(""));

    protected  <T> ParsingResult<T> parse(Rule rule, String input) {
        TracingParseRunner<T> handler = new TracingParseRunner<T>(rule);
        return handler.run(input);
    }
}
