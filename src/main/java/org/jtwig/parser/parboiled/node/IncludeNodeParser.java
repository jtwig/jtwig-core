package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.MapExpression;
import org.jtwig.model.tree.IncludeNode;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import java.util.HashMap;

public class IncludeNodeParser extends NodeParser<IncludeNode> {
    public IncludeNodeParser(ParserContext context) {
        super(IncludeNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        BooleanParser booleanParser = parserContext().parser(BooleanParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(), spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.INCLUDE), spacingParser.Mandatory(),
                Mandatory(anyExpressionParser.ExpressionRule(), "Include missing path expression"),
                FirstOf(
                        Sequence(
                                spacingParser.Spacing(),
                                String("ignore"), spacingParser.Mandatory(),
                                Mandatory(String("missing"), "Did you mean 'ignore missing'?"),
                                booleanParser.push(true)
                        ),
                        booleanParser.push(false)
                ),
                FirstOf(
                        Sequence(
                                spacingParser.Spacing(),
                                String("with"), spacingParser.Spacing(),
                                Mandatory(anyExpressionParser.ExpressionRule(), "Expecting map of values")
                        ),
                        anyExpressionParser.push(new MapExpression(positionTrackerParser.currentPosition(), new HashMap<String, Expression>()))
                ),
                FirstOf(
                        Sequence(
                                spacingParser.Spacing(),
                                String("only"),
                                booleanParser.push(true)
                        ),
                        booleanParser.push(false)
                ),

                spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Code island not closed"),
                push(new IncludeNode(
                        positionTrackerParser.pop(4),
                        new IncludeConfiguration(
                                anyExpressionParser.pop(3),
                                anyExpressionParser.pop(1),
                                booleanParser.pop(),
                                booleanParser.pop()
                        )
                ))

        );
    }
}
