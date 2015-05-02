package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.TextNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

import static org.parboiled.Parboiled.createParser;

public class TextNodeParser extends NodeParser<TextNode> {
    public TextNodeParser(ParserContext context) {
        super(TextNodeParser.class, context);
        createParser(TextBuilderParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        TextBuilderParser textBuilderParser = parserContext().parser(TextBuilderParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                textBuilderParser.Text(),
                push(new TextNode(
                        positionTrackerParser.pop(1),
                        textBuilderParser.pop().toString(),
                        new TextNode.Configuration().setTrimLeft(limitsParser.lastWhiteSpace()))),
                limitsParser.update(peek())
        );
    }

    public static class TextBuilderParser extends BasicParser<StringBuilder> {
        public TextBuilderParser(ParserContext context) {
            super(TextBuilderParser.class, context);
        }

        public Rule Text() {
            return Sequence(
                    push(new StringBuilder()),
                    OneOrMore(
                            TestNot(
                                    parserContext().parser(LimitsParser.class).anyEnd()
                            ),
                            ANY,
                            run(peek().append(match()))
                    )
            );
        }

        boolean run(StringBuilder append) {
            return true;
        }

    }
}
