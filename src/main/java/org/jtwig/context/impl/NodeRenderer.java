package org.jtwig.context.impl;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.NodeContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.model.tree.Node;
import org.jtwig.render.Renderable;

import java.util.Stack;

public class NodeRenderer {
    private final RenderContext renderContext;
    private final Stack<NodeContext> nodeContextStack;
    private final Stack<ValueContext> valueContextStack;

    public NodeRenderer(RenderContext renderContext, Stack<NodeContext> nodeContextStack,
                        Stack<ValueContext> valueContextStack) {
        this.renderContext = renderContext;
        this.nodeContextStack = nodeContextStack;
        this.valueContextStack = valueContextStack;
    }

    public Renderable render (Node node) {
        ValueContext valueContext = node.scopeType().getFactory().create(valueContextStack.peek());
        NodeContext context = new NodeContext(node, valueContext);
        valueContextStack.push(valueContext);
        nodeContextStack.push(context);
        Renderable render = node.render(renderContext);
        nodeContextStack.pop();
        valueContextStack.pop();
        return render;
    }

}
