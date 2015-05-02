package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.tree.IfNode;
import org.jtwig.model.tree.IfNode.IfConditionNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Collection;

import static org.parboiled.Parboiled.createParser;

public class IfNodeParser extends NodeParser<IfNode> {

    public IfNodeParser(ParserContext context) {
        super(IfNodeParser.class, context);
        createParser(IfConditionNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        IfConditionNodeParser conditionNodeParser = parserContext().parser(IfConditionNodeParser.class);
        return Sequence(
            positionTrackerParser.PushPosition(),
            conditionNodeParser.NodeRule(),
            push(new IfNode(positionTrackerParser.pop(1), conditionNodeParser.pop()))
        );
    }

    public static class IfConditionNodeParser extends BasicParser<Collection<IfConditionNode>> {

        public IfConditionNodeParser(ParserContext context) {
            super(IfConditionNodeParser.class, context);
        }

        Rule NodeRule() {
            PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
            LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
            LexicParser lexicParser = parserContext().parser(LexicParser.class);
            CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
            return Sequence(
                push(new ArrayList<IfConditionNode>()),

                // Start
                Sequence(
                    positionTrackerParser.PushPosition(),
                    limitsParser.startCode(), spacingParser.Spacing(),
                    lexicParser.Keyword(Keyword.IF)
                ),

                ifConditionExpression(limitsParser, spacingParser, anyExpressionParser),

                // Content
                compositeNodeParser.NodeRule(),

                peek(3).add(new IfConditionNode(
                    positionTrackerParser.pop(2),
                    anyExpressionParser.pop(1),
                    compositeNodeParser.pop()
                )),

                ZeroOrMore(
                    elseIfCondition()
                ),

                Optional(
                    elseCondition()
                ),

                // End
                Mandatory(Sequence(
                    limitsParser.startCode(), spacingParser.Spacing(),
                    lexicParser.Keyword(Keyword.END_IF), spacingParser.Spacing(),
                    Mandatory(limitsParser.endCode(), "If condition endif code island not closed")
                ), "Missing endif tag")
            );
        }

        public Rule ifConditionExpression(LimitsParser limitsParser, SpacingParser spacingParser, AnyExpressionParser anyExpressionParser) {
            return Sequence(
                spacingParser.Spacing(), Mandatory(String("("), "If condition expression must start with parentheses"),
                spacingParser.Spacing(), Mandatory(anyExpressionParser.ExpressionRule(), "Expecting an expression together with the if construction"),
                spacingParser.Spacing(), Mandatory(String(")"), "If condition expression must end with parentheses"),
                spacingParser.Spacing(), Mandatory(limitsParser.endCode(), "If condition code island not closed")
            );
        }

        Rule elseIfCondition() {
            PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
            LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
            LexicParser lexicParser = parserContext().parser(LexicParser.class);
            CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
            return Sequence(
                // Start
                Sequence(
                    positionTrackerParser.PushPosition(),

                    limitsParser.startCode(), spacingParser.Spacing(),
                    lexicParser.Keyword(Keyword.ELSE_IF)
                ),

                ifConditionExpression(limitsParser, spacingParser, anyExpressionParser),

                // Content
                compositeNodeParser.NodeRule(),
                peek(3).add(new IfConditionNode(
                    positionTrackerParser.pop(2),
                    anyExpressionParser.pop(1),
                    compositeNodeParser.pop()
                ))
            );
        }


        Rule elseCondition() {
            PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
            LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            LexicParser lexicParser = parserContext().parser(LexicParser.class);
            CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
            return Sequence(
                // Start
                Sequence(
                    positionTrackerParser.PushPosition(),
                    limitsParser.startCode(), spacingParser.Spacing(),
                    lexicParser.Keyword(Keyword.ELSE),
                    spacingParser.Spacing(),
                    Mandatory(limitsParser.endCode(), "Expecting ending of else block")
                ),

                compositeNodeParser.NodeRule(),
                peek(2).add(new IfConditionNode(
                    positionTrackerParser.pop(1),
                    new ConstantExpression(positionTrackerParser.currentPosition(), true),
                    compositeNodeParser.pop()
                ))
            );
        }

    }

}
