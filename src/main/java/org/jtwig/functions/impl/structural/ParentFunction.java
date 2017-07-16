package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.environment.Environment;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.functions.impl.structural.exceptions.ParentFunctionOutsideBlockException;
import org.jtwig.functions.impl.structural.exceptions.ParentFunctionWithoutExtending;
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

        // FIXME cleanup try/catches into sub-methods
        Environment env = request.getEnvironment();
        RenderContext ctx = request.getRenderContext();

        String identifier;
        try {
            identifier = ctx.getCurrent(BlockReference.class).getIdentifier();
        }
        catch (IllegalStateException e) {
            throw new ParentFunctionOutsideBlockException();
        }

        BlockContext blockContext;
        try {
            blockContext = request.getRenderContext().getCurrent(BlockContext.class);
        }
        catch (IllegalStateException e) {
            throw new ParentFunctionOutsideBlockException();
        }

        Optional<BlockDefinition> currentBlockDefinition = blockContext.pollFirst(identifier);
        //System.err.println("parent function in block "+currentBlockDefinition.get().getSource());

        if(!currentBlockDefinition.isPresent()) {
            throw new ParentFunctionOutsideBlockException();
        }


        Optional<BlockDefinition> blockDefinition = blockContext.get(identifier);
        //System.err.println("rendering block "+blockDefinition.get().getSource());

        if(!blockDefinition.isPresent()) {
            throw new ParentFunctionWithoutExtending();
        }

        Object renderBlock = NodeRenderHelper.renderBlock(request, blockDefinition);
        blockContext.addFirst(identifier, currentBlockDefinition.get());

        return renderBlock;
    }
}
