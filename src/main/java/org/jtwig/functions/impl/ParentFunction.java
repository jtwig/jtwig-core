package org.jtwig.functions.impl;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.annotations.JtwigFunction;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.Node;
import org.jtwig.model.tree.visitor.NodeVisitor;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.resource.Resource;
import org.jtwig.util.OptionalUtils;

public class ParentFunction {
    @JtwigFunction("parent")
    public String parent () {
        RenderContext renderContext = getRenderContext();

        String blockName = renderContext.currentResource().currentBlock()
                .or(OptionalUtils.<String, CalculationException>throwException(new CalculationException("Parent function can only be called inside a block element")));

        Resource resource = renderContext.currentResource().parent()
                .or(OptionalUtils.<Resource, CalculationException>throwException(new CalculationException("Currently there is no parent context")));

        FindBlockVisitor findBlockVisitor = new FindBlockVisitor(blockName);
        renderContext.environment().parser().parse(resource).visit(findBlockVisitor);

        Node content = findBlockVisitor.getContent().or(OptionalUtils.<Node, CalculationException>throwException(new CalculationException(String.format("No block %s found in parent", blockName))));

        return content.render(renderContext).appendTo(new StringBuilderRenderResult()).content();
    }

    protected RenderContext getRenderContext() {
        return RenderContextHolder.get();
    }

    private static class FindBlockVisitor implements NodeVisitor {
        private final String blockIdentifier;
        private Optional<Node> content;

        public FindBlockVisitor(String blockIdentifier) {
            this.blockIdentifier = blockIdentifier;
        }

        @Override
        public void visit(Node node) {
            if (node instanceof BlockNode) {
                BlockNode blockNode = (BlockNode) node;
                if (blockIdentifier.equals(blockNode.getIdentifier())) {
                    content = Optional.of(blockNode.getContent());
                }
            }
        }

        public Optional<Node> getContent() {
            return content;
        }
    }
}
