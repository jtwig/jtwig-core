package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.RenderResult;
import org.jtwig.renderable.StringBuilderRenderResult;

public class BlockFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "block";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(1);
        request.maximumNumberOfArguments(1);
        String name = request.getEnvironment().getValueEnvironment().getStringConverter().convert(request.get(0));

        Optional<BlockDefinition> nodeOptional = request.getRenderContext().getBlockContext().getCurrent().get(name);
        if (nodeOptional.isPresent()) {
            RenderNodeService renderNodeService = request.getEnvironment().getRenderEnvironment().getRenderNodeService();
            BlockDefinition definition = nodeOptional.get();
            request.getRenderContext().getResourceContext().start(definition.getSource());
            RenderResult result = renderNodeService.render(request, definition.getNode())
                    .appendTo(new StringBuilderRenderResult());
            request.getRenderContext().getResourceContext().end();
            return result.content();
        } else {
            return "";
        }
    }
}
