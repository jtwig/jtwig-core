package org.jtwig.render;

import org.jtwig.environment.Environment;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.listeners.RenderStage;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.IsolateParentValueContext;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;

import java.util.Iterator;
import java.util.Map;

public class RenderResourceService {
    public Renderable render(RenderRequest request, RenderResourceRequest renderResourceRequest) {
        Environment environment = request.getEnvironment();

        RenderNodeService renderNodeService = environment.getRenderEnvironment().getRenderNodeService();
        Node node = environment.getParser().parse(environment, renderResourceRequest.getResource());

        if (renderResourceRequest.isNewBlockContext()) {
            request.getRenderContext().start(BlockContext.class, BlockContext.newContext());
        }

        if (renderResourceRequest.isNewValueContext()) {
            request.getRenderContext().start(ValueContext.class, MapValueContext.newContext());
        } else {
            ValueContext current = request.getRenderContext().getCurrent(ValueContext.class);
            request.getRenderContext().start(ValueContext.class, new IsolateParentValueContext(current, MapValueContext.newContext()));
        }

        request.getRenderContext().start(ResourceReference.class, renderResourceRequest.getResource());
        request.getRenderContext().start(EscapeEngine.class, environment.getEscapeEnvironment().getInitialEscapeEngine());

        Iterator<Map.Entry<String, Object>> iterator = renderResourceRequest.getIncludeModel().iterator();
        ValueContext valueContextCurrent = request.getRenderContext().getCurrent(ValueContext.class);

        while (iterator.hasNext()) {
            Map.Entry<String, Object> item = iterator.next();
            valueContextCurrent.with(item.getKey(), item.getValue());
        }

        environment.getRenderEnvironment().getRenderListeners().trigger(RenderStage.PRE_RESOURCE_RENDER, request);

        Renderable renderable = renderNodeService.render(request, node);

        environment.getRenderEnvironment().getRenderListeners().trigger(RenderStage.POST_RESOURCE_RENDER, request);

        if (renderResourceRequest.isNewBlockContext()) {
            request.getRenderContext().end(BlockContext.class);
        }

        request.getRenderContext().end(ResourceReference.class);
        request.getRenderContext().end(ValueContext.class);
        request.getRenderContext().end(EscapeEngine.class);

        return renderable;
    }
}
