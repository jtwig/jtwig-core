package org.jtwig.parser.parboiled.node;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.MacroNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.List;

import static org.parboiled.Parboiled.createParser;

public class MacroNodeParser extends NodeParser<MacroNode> {
    public MacroNodeParser(ParserContext context) {
        super(MacroNodeParser.class, context);
        createParser(ParametersParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        ParametersParser parametersParser = parserContext().parser(ParametersParser.class);
        CompositeNodeParser nodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                // Start
                Sequence(
                        limitsParser.startCode(), spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.MACRO), spacingParser.Spacing(),
                        Mandatory(variableExpressionParser.ExpressionRule(), "Missing macro name"),
                        spacingParser.Spacing(),
                        Mandatory(parametersParser.Parameters(), "Missing macro arguments"),
                        spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Code island not closed"),
                        spacingParser.Spacing()
                ),

                nodeParser.NodeRule(),

                // End
                Mandatory(Sequence(
                        limitsParser.startCode(), spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.END_MACRO), spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Code island not closed")
                ), "Missing macroend tag"),

                push(MacroNode.create(
                        positionTrackerParser.pop(3),
                        variableExpressionParser.pop(2),
                        parametersParser.pop(1),
                        nodeParser.pop()
                ))

        );
    }

    public static class ParametersParser extends BasicParser<List<VariableExpression>> {
        public ParametersParser(ParserContext context) {
            super(ParametersParser.class, context);
        }

        Rule Parameters() {
            SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
            VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
            return Sequence(
                    push(new ArrayList<VariableExpression>()),

                    String("("), spacingParser.Spacing(),
                    Optional(
                            variableExpressionParser.ExpressionRule(),
                            spacingParser.Spacing(),
                            peek(1).add(variableExpressionParser.pop()),

                            ZeroOrMore(
                                    String(","), spacingParser.Spacing(),
                                    variableExpressionParser.ExpressionRule(),
                                    spacingParser.Spacing(),
                                    peek(1).add(variableExpressionParser.pop())
                            )
                    ),
                    String(")")
            );
        }
    }
}
