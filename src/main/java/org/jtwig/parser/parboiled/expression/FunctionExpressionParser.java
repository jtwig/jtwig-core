package org.jtwig.parser.parboiled.expression;

import com.google.common.base.Optional;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collection;

import static org.parboiled.Parboiled.createParser;

public class FunctionExpressionParser extends ExpressionParser<FunctionExpression> {
    public FunctionExpressionParser(ParserContext context) {
        super(FunctionExpressionParser.class, context);
        createParser(ArgumentsParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        ArgumentsParser argumentsParser = parserContext().parser(ArgumentsParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                variableExpressionParser.ExpressionRule(), spacingParser.Spacing(),
                argumentsParser.Arguments(), spacingParser.Spacing(),
                push(new FunctionExpression(
                        positionTrackerParser.pop(2),
                        variableExpressionParser.pop(1).getIdentifier(),
                        argumentsParser.pop()
                ))
        );
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
            VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
            PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
            return Sequence(FirstOf(
                            Sequence(
                                    variableExpressionParser.ExpressionRule(),
                                    spacingParser.Spacing(), String("="), spacingParser.Spacing()
                            ),
                            variableExpressionParser.push(new VariableExpression(positionTrackerParser.currentPosition(), null))
                    ),
                    anyExpressionParser.ExpressionRule(),
                    spacingParser.Spacing(),
                    peek(2).add(new Argument(Optional.fromNullable(variableExpressionParser.pop(1).getIdentifier()), anyExpressionParser.pop()))
            );
        }
    }
}
