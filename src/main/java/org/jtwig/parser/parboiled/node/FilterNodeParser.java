package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.model.tree.FilterNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.BinaryOperationExpressionParser;
import org.jtwig.parser.parboiled.expression.ExpressionParser;
import org.jtwig.parser.parboiled.expression.FunctionExpressionParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import static org.parboiled.Parboiled.createParser;

public class FilterNodeParser extends NodeParser<FilterNode> {
    public FilterNodeParser(ParserContext context) {
        super(FilterNodeParser.class, context);
        createParser(FilterExpressionParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        FilterExpressionParser filterExpressionParser = parserContext().parser(FilterExpressionParser.class);
        CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);

        return Sequence(
                positionTrackerParser.PushPosition(),

                // start
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.FILTER),
                        spacingParser.Spacing(),
                        filterExpressionParser.ExpressionRule(),
                        spacingParser.Spacing(),
                        limitsParser.endCode()
                ),

                // content
                compositeNodeParser.NodeRule(),

                // end
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.END_FILTER),
                        spacingParser.Spacing(),
                        limitsParser.endCode()
                ),

                push(new FilterNode(positionTrackerParser.pop(2), compositeNodeParser.pop(), filterExpressionParser.pop()))

        );
    }

    public static class FilterExpressionParser extends ExpressionParser<InjectableExpression> {
        public FilterExpressionParser(ParserContext context) {
            super(FilterExpressionParser.class, context);
        }

        @Override
        public Rule ExpressionRule() {
            return FirstOf(
                    parserContext().parser(BinaryOperationExpressionParser.class).ExpressionRule(),
                    parserContext().parser(FunctionExpressionParser.class).ExpressionRule(),
                    parserContext().parser(VariableExpressionParser.class).ExpressionRule()
            );
        }
    }
}
