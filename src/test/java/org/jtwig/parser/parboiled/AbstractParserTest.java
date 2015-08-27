package org.jtwig.parser.parboiled;

import org.jtwig.parser.DefaultJtwigParserConfiguration;
import org.jtwig.parser.JtwigParserConfiguration;
import org.jtwig.resource.StringResource;
import org.parboiled.Rule;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

public abstract class AbstractParserTest {
    private JtwigParserConfiguration parserConfiguration = new DefaultJtwigParserConfiguration();
    protected final ParserContext context = ParserContext.instance(new StringResource(""),
            parserConfiguration.getSyntaxConfiguration(),
            parserConfiguration.getAddonParserProviders(),
            parserConfiguration.getUnaryOperators(),
            parserConfiguration.getBinaryOperators());

    protected <T> ParsingResult<T> parse(Rule rule, String input) {
        ParseRunner<T> handler = new BasicParseRunner<T>(rule);
        return handler.run(input);
    }
}
