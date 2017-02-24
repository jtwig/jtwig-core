package org.jtwig.render;

import org.jtwig.environment.Environment;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.context.ContextItem;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.BlockContext;
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

        StackedContext<BlockContext> blockContext = request.getRenderContext().getBlockContext();
        StackedContext<ValueContext> valueContext = request.getRenderContext().getValueContext();
        StackedContext<ContextItem<ResourceReference>> resourceContext = request.getRenderContext().getResourceContext();
        StackedContext<EscapeEngine> escapeEngineContext = request.getRenderContext().getEscapeEngineContext();

        if (renderResourceRequest.isNewBlockContext()) {
            blockContext.start(BlockContext.newContext());
        }

        if (renderResourceRequest.isNewValueContext()) {
            valueContext.start(MapValueContext.newContext());
        } else {
            valueContext.start(new IsolateParentValueContext(valueContext.getCurrent(), MapValueContext.newContext()));
        }

        resourceContext.start(new ContextItem<>(renderResourceRequest.getResource()));
        escapeEngineContext.start(environment.getEscapeEnvironment().getInitialEscapeEngine());

        Iterator<Map.Entry<String, Object>> iterator = renderResourceRequest.getIncludeModel().iterator();
        ValueContext valueContextCurrent = valueContext.getCurrent();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> item = iterator.next();
            valueContextCurrent.with(item.getKey(), item.getValue());
        }

        Renderable renderable = renderNodeService.render(request, node);

        if (renderResourceRequest.isNewBlockContext()) {
            blockContext.end();
        }
        resourceContext.end();
        valueContext.end();
        escapeEngineContext.end();

        return renderable;
    }
}
