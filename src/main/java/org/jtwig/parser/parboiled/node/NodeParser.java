package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.parboiled.Rule;

public abstract class NodeParser<T extends Node> extends BasicParser<T> {
    public NodeParser(Class<? extends BasicParser> type, ParserContext context) {
        super(type, context);
    }

    public abstract Rule NodeRule ();
}
