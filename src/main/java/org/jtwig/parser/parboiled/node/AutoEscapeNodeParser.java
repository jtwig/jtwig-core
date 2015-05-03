package org.jtwig.parser.parboiled.node;

import com.google.common.base.Optional;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.*;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.expression.StringExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;
import org.parboiled.annotations.Label;

import static org.parboiled.Parboiled.createParser;

public class AutoEscapeNodeParser extends NodeParser<AutoEscapeNode> {
    public AutoEscapeNodeParser(ParserContext context) {
        super(AutoEscapeNodeParser.class, context);
        createParser(EscapeModeParser.class, context);
    }

    @Override
    @Label("Auto Escape")
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        EscapeModeParser escapeModeParser = parserContext().parser(EscapeModeParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        CompositeNodeParser nodeParser = parserContext().parser(CompositeNodeParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),

                // Open Tag
                Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.AUTO_ESCAPE),
                        spacingParser.Spacing(),
                        FirstOf(
                                Sequence(
                                        escapeModeParser.EscapeMode(),
                                        spacingParser.Spacing()
                                ),
                                escapeModeParser.push(null)
                        ),
                        Mandatory(limitsParser.endCode(), "Missing code island end")
                ),

                nodeParser.NodeRule(),

                // End
                Mandatory(Sequence(
                        limitsParser.startCode(),
                        spacingParser.Spacing(),
                        lexicParser.Keyword(Keyword.END_AUTO_ESCAPE),
                        spacingParser.Spacing(),
                        Mandatory(limitsParser.endCode(), "Missing code island end")
                ), "Missing endautoescape tag"),

                push(new AutoEscapeNode(positionTrackerParser.pop(2), nodeParser.pop(), Optional.fromNullable(escapeModeParser.pop())))
        );
    }

    public static class EscapeModeParser extends BasicParser<EscapeMode> {
        public EscapeModeParser(ParserContext context) {
            super(EscapeModeParser.class, context);
        }

        public Rule EscapeMode() {
            StringExpressionParser parser = parserContext().parser(StringExpressionParser.class);
            return FirstOf(
                    Sequence(
                            parser.ExpressionRule(),
                            push(EscapeMode.valueOf(parser.pop().getConstantValue().toString().toUpperCase()))
                    ),
                    push(null)
            );
        }
    }
}
