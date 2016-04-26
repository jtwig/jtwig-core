package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class BlockNodeParser extends NodeParser<BlockNode> {
    public BlockNodeParser(ParserContext context) {
        super(BlockNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        CompositeNodeParser compositeNodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                Sequence(
                        limitsParser.startCode(), spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.BLOCK),
                        spacingParser.Mandatory(),
                        Mandatory(variableExpressionParser.ExpressionRule(), "Block identifier not specified"),
                        spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Missing end of code island")
                ),

                compositeNodeParser.NodeRule(),

                Mandatory(Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.END_BLOCK),
                        Optional(
                                spacingParser.Mandatory(),
                                variableExpressionParser.ExpressionRule(),
                                throwExceptionIfNonSameVariableName()
                        ),
                        spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Missing end of code island")
                ), "Missing endblock tag"),

                push(new BlockNode(
                        positionTrackerParser.pop(2),
                        variableExpressionParser.pop(1),
                        compositeNodeParser.pop()
                ))
        );
    }

    boolean throwExceptionIfNonSameVariableName() {
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        VariableExpression expression = variableExpressionParser.pop();
        VariableExpression original = variableExpressionParser.peek(1);

        if (!expression.getIdentifier().equals(original.getIdentifier())) {
            addError(String.format("Expecting block '%s' to end with the same identifier but found '%s' instead", original.getIdentifier(), expression.getIdentifier()));
        }
        return true;
    }

}
