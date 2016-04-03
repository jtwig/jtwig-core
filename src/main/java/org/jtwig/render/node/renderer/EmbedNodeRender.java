package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.model.tree.EmbedNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.RenderResourceRequest;
import org.jtwig.render.RenderResourceService;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class EmbedNodeRender implements NodeRender<EmbedNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, EmbedNode node) {
        CalculateExpressionService calculateExpressionService = renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Object path = calculateExpressionService.calculate(renderRequest, node.getResourceExpression());
        Resource current = renderRequest.getRenderContext().getResourceContext().getCurrent();
        Optional<Resource> resource = renderRequest.getEnvironment().getResourceEnvironment().getResourceResolver().resolve(renderRequest.getEnvironment(), current, getString(renderRequest, path));

        if (resource.isPresent()) {
            Converter<WrappedCollection> collectionConverter = renderRequest.getEnvironment().getValueEnvironment().getCollectionConverter();
            RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();
            RenderResourceService renderResourceService = renderRequest.getEnvironment().getRenderEnvironment().getRenderResourceService();

            Object mapValue = calculateExpressionService.calculate(renderRequest, node.getMapExpression());
            WrappedCollection includeModel = collectionConverter.convert(mapValue).or(WrappedCollection.empty());

            renderRequest.getRenderContext().getBlockContext().start(BlockContext.newContext());
            for (Node subNode : node.getNodes()) {
                renderNodeService.render(renderRequest, subNode);
            }

            Renderable renderable = renderResourceService.render(renderRequest, new RenderResourceRequest(resource.get(), false, !node.isInheritModel(), includeModel));

            renderRequest.getRenderContext().getBlockContext().end();
            return renderable;
        } else {
            if (node.isIgnoreMissing()) {
                return EmptyRenderable.instance();
            } else {
                throw new ResourceNotFoundException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Resource '%s' not found", path)));
            }
        }
    }

    private String getString(RenderRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(input);
    }
}
