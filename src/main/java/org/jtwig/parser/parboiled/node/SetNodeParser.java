package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.SetNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class SetNodeParser extends NodeParser<SetNode> {
    public SetNodeParser(ParserContext context) {
        super(SetNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(), spacingParser.Spacing(),
                parserContext().parser(LexicParser.class).Keyword(Keyword.SET), spacingParser.Mandatory(),
                variableExpressionParser.ExpressionRule(), spacingParser.Spacing(),
                Mandatory(String("="), "Expecting attribution operation '='"),
                spacingParser.Spacing(),
                anyExpressionParser.ExpressionRule(), spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Expecting end of set code island"),
                push(new SetNode(
                        positionTrackerParser.pop(2),
                        (VariableExpression) anyExpressionParser.pop(1),
                        anyExpressionParser.pop()
                ))
        );
    }

}
