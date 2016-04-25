package org.jtwig.parser;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.reference.ResourceReference;

public interface JtwigParser {
    Node parse (Environment environment, ResourceReference resource);
}
