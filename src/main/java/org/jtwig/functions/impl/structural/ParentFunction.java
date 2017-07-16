package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.render.context.RenderContext;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.context.model.BlockReference;

public class ParentFunction extends SimpleJtwigFunction {
    @Override
    public String name() {
        return "parent";
    }

    @Override
    public Object execute(FunctionRequest request) {
        request.minimumNumberOfArguments(0);
        request.maximumNumberOfArguments(0);

        Environment env = request.getEnvironment();
        RenderContext ctx = request.getRenderContext();

        String blockIdentifier = ctx.getCurrent(BlockReference.class).getIdentifier();

        BlockContext blockContext = request.getRenderContext().getCurrent(BlockContext.class);
        Optional<BlockDefinition> blockDefinition = blockContext.get(blockIdentifier, 1);

        if(!blockDefinition.isPresent()) {
            throw new IllegalStateException("Call to parent() in a Block that does not extend another Block.");
        }

        return NodeRenderHelper.renderBlock(request, blockDefinition);
    }
}
