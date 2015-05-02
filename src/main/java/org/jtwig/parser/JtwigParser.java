package org.jtwig.parser;

import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;

import java.io.InputStream;

public interface JtwigParser {
    Node parse (Resource resource);
}
