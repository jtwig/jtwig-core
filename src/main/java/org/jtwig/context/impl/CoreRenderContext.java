package org.jtwig.context.impl;

import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.EscapeModeContext;
import org.jtwig.context.model.NodeContext;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.util.JtwigValue;

import java.util.HashMap;
import java.util.Stack;

public class CoreRenderContext implements RenderContext {
    private final Configuration configuration;
    private final Stack<ValueContext> valueContextStack;
    private final Stack<ResourceContext> resourceContextStack;
    private final Stack<NodeContext> nodeContextStack;
    private final EscapeModeContext escapeModeContext;

    public CoreRenderContext(Configuration configuration,
                             Stack<ValueContext> valueContextStack,
                             Stack<ResourceContext> resourceContextStack,
                             Stack<NodeContext> nodeContextStack,
                             EscapeModeContext escapeModeContext) {
        this.configuration = configuration;
        this.valueContextStack = valueContextStack;
        this.resourceContextStack = resourceContextStack;
        this.nodeContextStack = nodeContextStack;
        this.escapeModeContext = escapeModeContext;
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public ResourceRenderer resourceRenderer() {
        return new ResourceRenderer(this, resourceContextStack, valueContextStack, new SimpleValueContext(new HashMap<String, JtwigValue>()));
    }

    @Override
    public NodeRenderer nodeRenderer() {
        return new NodeRenderer(this, nodeContextStack, valueContextStack);
    }

    @Override
    public ResourceContext currentResource() {
        return resourceContextStack.peek();
    }

    @Override
    public ValueContext valueContext() {
        return valueContextStack.peek();
    }

    @Override
    public NodeContext currentNode() {
        return nodeContextStack.peek();
    }

    @Override
    public EscapeModeContext escapeContext() {
        return escapeModeContext;
    }
}
