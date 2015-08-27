package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.ForLoopNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class ForLoopNodeParser extends NodeParser<ForLoopNode> {

    public ForLoopNodeParser(ParserContext context) {
        super(ForLoopNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
            positionTrackerParser.PushPosition(),

            // Start
            Sequence(
                limitsParser.startCode(), spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.FOR), spacingParser.Spacing(),
                Mandatory(variableExpressionParser.ExpressionRule(), "Expecting a variable name in for loop. Example: {% for value in list %}"),
                spacingParser.Spacing(),
                FirstOf(
                    Sequence(
                        String(","), spacingParser.Spacing(),
                        Mandatory(variableExpressionParser.ExpressionRule(), "Expecting a second variable name in for loop. Example: {% for key, value in list %}"),
                        spacingParser.Spacing()
                    ),
                    variableExpressionParser.push(null)
                ),
                Mandatory(lexicParser.Keyword(Keyword.IN), "Malformed for loop, missing 'in' keyword. For example: {% for i in list %}"), spacingParser.Spacing(),
                Mandatory(anyExpressionParser.ExpressionRule(), "Expecting an expression in for loop"),
                spacingParser.Spacing(),

                Mandatory(limitsParser.endCode(), "Malformed for loop start syntax, missing code island ending symbol")
            ),

            compositeNodeParser.NodeRule(),

            // End
            Mandatory(Sequence(
                limitsParser.startCode(), spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.END_FOR), spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Malformed for loop end syntax, missing code island ending symbol")
            ), "Missing endfor tag"),

            push(new ForLoopNode(
                positionTrackerParser.pop(4),
                variableExpressionParser.pop(3),
                variableExpressionParser.pop(2),
                variableExpressionParser.pop(1),
                compositeNodeParser.pop()
            ))
        );
    }
}
