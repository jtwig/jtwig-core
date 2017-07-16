package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.StringBuilderRenderResult;
import org.jtwig.resource.reference.ResourceReference;

class NodeRenderHelper {
    public static Object renderBlock(FunctionRequest renderRequest, Optional<BlockDefinition> blockDefinition) {
        if (blockDefinition.isPresent()) {
            return renderBlock(renderRequest, blockDefinition.get());
        } else {
            return "";
        }
    }

    public static Object renderBlock(FunctionRequest renderRequest, BlockDefinition blockDefinition) {
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();
        renderRequest.getRenderContext().start(ResourceReference.class, blockDefinition.getSource());
        RenderResult result = renderNodeService.render(renderRequest, blockDefinition.getNode())
                .appendTo(new StringBuilderRenderResult());

        renderRequest.getRenderContext().end(ResourceReference.class);
        return result.content();
    }
}
