package org.jtwig.context;

import org.jtwig.configuration.Configuration;
import org.jtwig.context.impl.NodeRenderer;
import org.jtwig.context.impl.ResourceRenderer;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.NodeContext;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.context.values.ValueContext;

public interface RenderContext {
    Configuration configuration();
    ResourceRenderer resourceRenderer();
    NodeRenderer nodeRenderer();
    ResourceContext currentResource();
    ValueContext valueContext();
    NodeContext currentNode();
    EscapeMode escapeMode();
}
