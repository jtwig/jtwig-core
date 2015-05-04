package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.SpacelessNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class SpacelessNodeParser extends NodeParser<SpacelessNode> {
    public SpacelessNodeParser(ParserContext context) {
        super(SpacelessNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                // start
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.SPACELESS),
                        spacingParser.Spacing(),
                        limitsParser.endCode()
                ),

                // content
                compositeNodeParser.NodeRule(),

                // stop
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.END_SPACELESS),
                        spacingParser.Spacing(),
                        limitsParser.endCode()
                ),

                push(new SpacelessNode(positionTrackerParser.pop(1), compositeNodeParser.pop()))
        );
    }
}
