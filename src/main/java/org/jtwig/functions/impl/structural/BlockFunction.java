package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.context.model.BlockDefinition;
import org.jtwig.render.context.model.BlockReference;

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

        Optional<BlockDefinition> blockDefinition = request.getRenderContext().getCurrent(BlockContext.class).get(name);
        request.getRenderContext().start(BlockReference.class, new BlockReference(name));
        Object renderable = NodeRenderHelper.renderBlock(request, blockDefinition);
        request.getRenderContext().end(BlockReference.class);
        return renderable;
    }
}
