package org.jtwig.render;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.value.context.IsolateParentValueContext;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;

import java.util.Iterator;
import java.util.Map;

public class RenderResourceService {
    public Renderable render(RenderRequest request, RenderResourceRequest renderResourceRequest) {
        RenderNodeService renderNodeService = request.getEnvironment().getRenderEnvironment().getRenderNodeService();
        Node node = request.getEnvironment().getParser().parse(renderResourceRequest.getResource());

        StackedContext<BlockContext> blockContext = request.getRenderContext().getBlockContext();
        StackedContext<ValueContext> valueContext = request.getRenderContext().getValueContext();
        StackedContext<Resource> resourceContext = request.getRenderContext().getResourceContext();
        StackedContext<EscapeEngine> escapeEngineContext = request.getRenderContext().getEscapeEngineContext();

        if (renderResourceRequest.isNewBlockContext()) {
            blockContext.start(BlockContext.newContext());
        }

        if (renderResourceRequest.isNewValueContext()) {
            valueContext.start(MapValueContext.newContext());
        } else {
            valueContext.start(new IsolateParentValueContext(valueContext.getCurrent(), MapValueContext.newContext()));
        }

        resourceContext.start(renderResourceRequest.getResource());
        escapeEngineContext.start(request.getEnvironment().getEscapeEnvironment().getInitialEscapeEngine());

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
