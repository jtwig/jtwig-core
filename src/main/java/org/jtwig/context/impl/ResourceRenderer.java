package org.jtwig.context.impl;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.Macro;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.context.values.ScopedValueContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ResourceRenderer {
    private final RenderContext renderContext;
    private final Stack<ResourceContext> resourceContextStack;
    private final Stack<ValueContext> valueContextStack;
    private final ValueContext valueContext;
    private final Map<String, Renderable> blocks;
    private boolean inheritModel = true;

    public ResourceRenderer(RenderContext renderContext,
                            Stack<ResourceContext> resourceContextStack,
                            Stack<ValueContext> valueContextStack,
                            Map<String, Renderable> blocks, ValueContext valueContext) {
        this.renderContext = renderContext;
        this.resourceContextStack = resourceContextStack;
        this.valueContextStack = valueContextStack;
        this.blocks = blocks;
        this.valueContext = valueContext;
    }

    public ResourceRenderer inheritModel(boolean inherit) {
        this.inheritModel = inherit;
        return this;
    }

    public ResourceRenderer define(String variableName, Object value) {
        valueContext.add(variableName, value);
        return this;
    }

    public ResourceRenderer define(Map<Object, Object> map) {
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            valueContext.add(entry.getKey().toString(), entry.getValue());
        }
        return this;
    }

    public ResourceRenderResult render(Resource resource) {
        ValueContext valueContext = valueContext(inheritModel);
        ResourceContext resourceContext = new ResourceContext(resource, new HashMap<String, Macro>(), blocks, valueContext);
        valueContextStack.push(valueContext);
        resourceContextStack.push(resourceContext);

        Renderable render = renderContext.configuration()
                .parser()
                .parse(resource)
                .render(renderContext);

        resourceContextStack.pop();
        valueContextStack.pop();

        return new ResourceRenderResult(resource, resourceContext, render);
    }

    private ValueContext valueContext(boolean shareParent) {
        if (shareParent) {
            return new ScopedValueContext(valueContextStack.peek(), valueContext);
        } else {
            return valueContext;
        }
    }

    public ResourceRenderer blocks(Map<String, Renderable> blocks) {
        this.blocks.putAll(blocks);
        return this;
    }
}
