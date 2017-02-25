package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.ImportSelfNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

public class ImportSelfNodeParser extends NodeParser<ImportSelfNode> {
    private static final String SELF = "_self";

    public ImportSelfNodeParser(ParserContext context) {
        super(ImportSelfNodeParser.class, context);
    }

    @Override
    @Label("Import Self Node")
    public Rule NodeRule() {
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(),
                spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.IMPORT),
                spacingParser.Spacing(),
                lexicParser.Keyword(SELF),
                spacingParser.Spacing(),
                Mandatory(String("as"), "Wrong syntax expecting token 'as'"),
                spacingParser.Spacing(),
                Mandatory(variableExpressionParser.ExpressionRule(), "Missing alias declaration"),
                spacingParser.Spacing(),
                Mandatory(limitsParser.endCode(), "Code island not closed"),

                push(new ImportSelfNode(
                        positionTrackerParser.pop(1),
                        variableExpressionParser.pop()
                ))
        );
    }
}
