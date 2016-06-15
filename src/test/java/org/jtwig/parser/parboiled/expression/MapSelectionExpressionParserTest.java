package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.config.DefaultJtwigParserConfiguration;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Test;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

public class MapSelectionExpressionParserTest {

    @Test
    public void expressionRule() throws Exception {
        DefaultJtwigParserConfiguration config = new DefaultJtwigParserConfiguration();
        ParserContext context = ParserContext.instance(
                ResourceReference.inline("Hello"),
                config,
                config.getAddonParserProviders(),
                config.getUnaryOperators(),
                config.getBinaryOperators(),
                config.getTestExpressionParsers()
        );

        MapSelectionExpressionParser parser = context.parser(MapSelectionExpressionParser.class);

        TracingParseRunner<Expression> runner = new TracingParseRunner<>(parser.ExpressionRule());
        ParsingResult<Expression> result = runner.run("list[position.size]");
        Expression resultValue = result.resultValue;

        System.out.println(resultValue);
    }
}