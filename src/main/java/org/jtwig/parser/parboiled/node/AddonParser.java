package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.parboiled.ParserContext;

public abstract class AddonParser extends NodeParser<Node> {

    public AddonParser(Class type, ParserContext context) {
        super(type, context);
    }
}
