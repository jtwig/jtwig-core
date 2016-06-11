package org.jtwig.parser.parboiled;

import org.jtwig.parser.config.DefaultJtwigParserConfiguration;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.resource.reference.ResourceReference;
import org.parboiled.Rule;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.ParsingResult;

public abstract class AbstractParserTest {
    private JtwigParserConfiguration parserConfiguration = new DefaultJtwigParserConfiguration();
    protected final ParserContext context = ParserContext.instance(new ResourceReference(ResourceReference.STRING, ""),
            parserConfiguration,
            parserConfiguration.getAddonParserProviders(),
            parserConfiguration.getUnaryOperators(),
            parserConfiguration.getBinaryOperators(),
            parserConfiguration.getTestExpressionParsers());

    protected <T> ParsingResult<T> parse(Rule rule, String input) {
        ParseRunner<T> handler = new BasicParseRunner<T>(rule);
        return handler.run(input);
    }
}
