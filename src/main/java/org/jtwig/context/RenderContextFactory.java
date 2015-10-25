package org.jtwig.context;

import org.jtwig.context.impl.CoreRenderContext;
import org.jtwig.context.model.*;
import org.jtwig.context.values.ValueContext;
import org.jtwig.environment.Environment;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;

import java.util.HashMap;
import java.util.Stack;

public class RenderContextFactory {
    public RenderContext create(ValueContext model, Resource template, Environment environment) {
        Stack<ValueContext> valueContextStack = new Stack<>();
        Stack<ResourceContext> resourceContextStack = new Stack<>();
        Stack<NodeContext> nodeContextStack = new Stack<>();
        valueContextStack.push(model);
        resourceContextStack.push(new ResourceContext(template, new HashMap<String, Macro>(), new HashMap<String, Renderable>(), model));
        Stack<EscapeMode> escapeContextStack = new Stack<>();
        escapeContextStack.push(environment.rendering().getInitialEscapeMode());
        EscapeModeContext escapeModeContext = new EscapeModeContext(escapeContextStack);
        return new CoreRenderContext(environment, valueContextStack, resourceContextStack, nodeContextStack, escapeModeContext);
    }
}
