package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.EnumeratedListExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

import java.util.ArrayList;
import java.util.Collection;

import static org.parboiled.Parboiled.createParser;

public class EnumerationListExpressionParser extends ExpressionParser<EnumeratedListExpression> {
    public EnumerationListExpressionParser(ParserContext context) {
        super(EnumerationListExpressionParser.class, context);
        createParser(MultipleExpressionsParser.class, context);
    }

    @Override
    @Label("List Enumeration")
    public Rule ExpressionRule() {
        MultipleExpressionsParser expressionsParser = parserContext().parser(MultipleExpressionsParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                String("["),
                spacingParser.Spacing(),
                expressionsParser.Rule(),
                spacingParser.Spacing(),
                Mandatory(String("]"), "Expecting end bracket"),
                push(new EnumeratedListExpression(positionTrackerParser.pop(1), expressionsParser.pop()))
        );
    }

    public static class MultipleExpressionsParser extends BasicParser<Collection<Expression>> {
        public MultipleExpressionsParser(ParserContext context) {
            super(MultipleExpressionsParser.class, context);
        }

        public Rule Rule() {
            AnyExpressionParser parser = parserContext().parser(AnyExpressionParser.class);
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            return Sequence(
                    push(new ArrayList<Expression>()),
                    Optional(
                            parser.ExpressionRule(),
                            peek(1).add(parser.pop()),
                            ZeroOrMore(
                                    spacingParser.Spacing(),
                                    String(","),
                                    spacingParser.Spacing(),
                                    parser.ExpressionRule(),
                                    peek(1).add(parser.pop())
                            )
                    )
            );
        }
    }
}
