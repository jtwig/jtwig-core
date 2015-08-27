package org.jtwig.parser.parboiled;

import org.jtwig.model.tree.Node;
import org.jtwig.model.tree.TextNode;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.node.CompositeNodeParser;
import org.jtwig.parser.parboiled.node.ExtendsNodeParser;
import org.jtwig.parser.parboiled.node.NodeParser;
import org.parboiled.Rule;

public class DocumentParser extends NodeParser<Node> {

    public DocumentParser(ParserContext context) {
        super(DocumentParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        return Sequence(
            FirstOf(
                parserContext().parser(ExtendsNodeParser.class).NodeRule(),
                parserContext().parser(CompositeNodeParser.class).NodeRule(),
                push(new TextNode(parserContext().parser(PositionTrackerParser.class).currentPosition(), "", new TextNode.Configuration()))
            ),
            EOI
        );
    }
}
