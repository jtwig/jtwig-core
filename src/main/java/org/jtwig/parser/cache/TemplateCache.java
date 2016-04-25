package org.jtwig.parser.cache;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.resource.reference.ResourceReference;

public interface TemplateCache {
    Node get (JtwigParser parser, Environment environment, ResourceReference resource);
}
