package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collection;

import static org.parboiled.Parboiled.createParser;

public class FunctionExpressionParser extends ExpressionParser<FunctionExpression> {
    public FunctionExpressionParser(ParserContext context) {
        super(FunctionExpressionParser.class, context);
        createParser(ArgumentsParser.class, context);
        createParser(FunctionNameParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        FunctionNameParser functionNameParser = parserContext().parser(FunctionNameParser.class);
        ArgumentsParser argumentsParser = parserContext().parser(ArgumentsParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                functionNameParser.Name(), spacingParser.Spacing(),
                argumentsParser.Arguments(), spacingParser.Spacing(),
                push(new FunctionExpression(
                        positionTrackerParser.pop(2),
                        functionNameParser.pop(1),
                        argumentsParser.pop()
                ))
        );
    }

    public static class FunctionNameParser extends BasicParser<String> {
        public FunctionNameParser(ParserContext context) {
            super(FunctionNameParser.class, context);
        }

        public Rule Name () {
            return FirstOf(
                    Sequence(
                            parserContext().parser(LexicParser.class).Keyword(Keyword.BLOCK),
                            push("block")
                    ),
                    Sequence(
                            parserContext().parser(LexicParser.class).Identifier(),
                            push(match())
                    )
            );
        }
    }

    public static class ArgumentsParser extends BasicParser<Collection<Argument>> {
        public ArgumentsParser(ParserContext context) {
            super(ArgumentsParser.class, context);
        }

        public Rule Arguments() {
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            return Sequence(
                    push(new ArrayList<Argument>()),
                    "(", spacingParser.Spacing(),
                    Optional(
                            ArgumentExpression(),
                            ZeroOrMore(
                                    String(","), spacingParser.Spacing(),
                                    ArgumentExpression()
                            )
                    ),
                    ")"
            );
        }

        Rule ArgumentExpression() {
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
            return Sequence(
                    anyExpressionParser.ExpressionRule(),
                    spacingParser.Spacing(),
                    peek(1).add(new Argument(anyExpressionParser.pop()))
            );
        }
    }
}
