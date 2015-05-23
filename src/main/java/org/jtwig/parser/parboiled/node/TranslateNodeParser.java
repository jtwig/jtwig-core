package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.TranslateNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class TranslateNodeParser extends NodeParser<TranslateNode> {
    public TranslateNodeParser(ParserContext context) {
        super(TranslateNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.TRANSLATE),
                        FirstOf(
                                Sequence(
                                        spacingParser.Spacing(),
                                        lexicParser.Keyword(Keyword.WITH),
                                        spacingParser.Spacing(),
                                        anyExpressionParser.ExpressionRule()
                                ),
                                anyExpressionParser.push(null)
                        ),
                        FirstOf(
                                Sequence(
                                        spacingParser.Spacing(),
                                        lexicParser.Keyword(Keyword.INTO),
                                        spacingParser.Spacing(),
                                        anyExpressionParser.ExpressionRule()
                                ),
                                anyExpressionParser.push(null)
                        ),

                        spacingParser.Spacing(),
                        limitsParser.endCode()
                ),

                compositeNodeParser.NodeRule(),

                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.END_TRANSLATE),
                        spacingParser.Spacing(),
                        limitsParser.endCode()
                ),

                push(new TranslateNode(positionTrackerParser.pop(3), compositeNodeParser.pop(), anyExpressionParser.pop(1), anyExpressionParser.pop()))
        );
    }
}
