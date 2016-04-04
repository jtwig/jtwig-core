package org.jtwig.parser.cache;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.resource.Resource;

public interface TemplateCache {
    Node get (Resource resource, JtwigParser parser);
}
