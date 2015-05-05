package org.jtwig.context;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.impl.CoreRenderContext;
import org.jtwig.context.model.*;
import org.jtwig.context.values.SimpleValueContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.render.impl.OverrideRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.value.JtwigValue;

import java.util.HashMap;
import java.util.Stack;

public class RenderContextBuilder implements Builder<RenderContext> {



    public static RenderContextBuilder renderContext() {
        return new RenderContextBuilder();
    }

    private RenderContextBuilder() {}

    private Resource resource;
    private Configuration configuration;
    private EscapeMode initialScapeMode = EscapeMode.NONE;
    private ValueContext valueContext = new SimpleValueContext(new HashMap<String, JtwigValue>());

    public RenderContextBuilder withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public RenderContextBuilder withValueContext(ValueContext valueContext) {
        this.valueContext = valueContext;
        return this;
    }

    public RenderContextBuilder withResource(Resource template) {
        this.resource = template;
        return this;
    }

    public RenderContextBuilder withInitialEscapeMode (EscapeMode escapeMode) {
        this.initialScapeMode = escapeMode;
        return this;
    }

    @Override
    public RenderContext build() {
        Stack<ValueContext> valueContextStack = new Stack<>();
        Stack<ResourceContext> resourceContextStack = new Stack<>();
        Stack<NodeContext> nodeContextStack = new Stack<>();
        valueContextStack.push(valueContext);
        resourceContextStack.push(new ResourceContext(resource, new HashMap<String, Macro>(), new HashMap<String, OverrideRenderable>(), valueContext));
        Stack<EscapeMode> escapeContextStack = new Stack<>();
        escapeContextStack.push(initialScapeMode);
        EscapeModeContext escapeModeContext = new EscapeModeContext(escapeContextStack);
        return new CoreRenderContext(configuration, valueContextStack, resourceContextStack, nodeContextStack, escapeModeContext);
    }
}
