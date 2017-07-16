package org.jtwig.functions.impl.structural;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.SimpleJtwigFunction;
import org.jtwig.functions.impl.structural.exceptions.ParentFunctionOutsideBlockException;
import org.jtwig.functions.impl.structural.exceptions.ParentFunctionWithoutExtending;
import org.jtwig.render.RenderRequest;
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

        String identifier = getBlockIdentifier(request);
        BlockContext blockContext = getBlockContext(request);

        BlockDefinition currentBlockDefinition = pollCurrentBlockDefinition(identifier, blockContext);
        BlockDefinition parentBlockDefinition = getParentBlockDefinition(identifier, blockContext);

        Object renderBlock = NodeRenderHelper.renderBlock(request, parentBlockDefinition);

        restoreCurrentBlockDefinition(identifier, blockContext, currentBlockDefinition);

        return renderBlock;
    }

    private void restoreCurrentBlockDefinition(String identifier, BlockContext blockContext, BlockDefinition currentBlockDefinition) {
        blockContext.addFirst(identifier, currentBlockDefinition);
    }

    private BlockDefinition getParentBlockDefinition(String identifier, BlockContext blockContext) {
        Optional<BlockDefinition> parentBlockDefinitionOptional = blockContext.get(identifier);

        if (!parentBlockDefinitionOptional.isPresent()) {
            throw new ParentFunctionWithoutExtending();
        }

        return parentBlockDefinitionOptional.get();
    }

    private BlockDefinition pollCurrentBlockDefinition(String identifier, BlockContext blockContext) {
        Optional<BlockDefinition> blockDefinitionOptional = blockContext.pollFirst(identifier);

        if (!blockDefinitionOptional.isPresent()) {
            throw new ParentFunctionOutsideBlockException();
        }

        return blockDefinitionOptional.get();
    }

    private BlockContext getBlockContext(RenderRequest request) {
        try {
            return request.getRenderContext().getCurrent(BlockContext.class);
        } catch (IllegalStateException e) {
            throw new ParentFunctionOutsideBlockException();
        }
    }

    private String getBlockIdentifier(RenderRequest request) {
        try {
            return request.getRenderContext().getCurrent(BlockReference.class).getIdentifier();
        } catch (IllegalStateException e) {
            throw new ParentFunctionOutsideBlockException();
        }
    }
}
