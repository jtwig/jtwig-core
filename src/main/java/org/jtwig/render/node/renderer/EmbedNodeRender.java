package org.jtwig.render.node.renderer;

import org.jtwig.environment.Environment;
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
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class EmbedNodeRender implements NodeRender<EmbedNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, EmbedNode node) {
        Environment environment = renderRequest.getEnvironment();
        CalculateExpressionService calculateExpressionService = environment.getRenderEnvironment().getCalculateExpressionService();
        Object path = calculateExpressionService.calculate(renderRequest, node.getResourceExpression());
        ResourceReference current = renderRequest.getRenderContext().getCurrent(ResourceReference.class);
        ResourceService resourceService = environment.getResourceEnvironment().getResourceService();
        ResourceReference newReference = resourceService.resolve(current, getString(renderRequest, path));
        ResourceMetadata resourceMetadata = resourceService.loadMetadata(newReference);

        if (resourceMetadata.exists()) {
            Converter<WrappedCollection> collectionConverter = environment.getValueEnvironment().getCollectionConverter();
            RenderNodeService renderNodeService = environment.getRenderEnvironment().getRenderNodeService();
            RenderResourceService renderResourceService = environment.getRenderEnvironment().getRenderResourceService();

            Object mapValue = calculateExpressionService.calculate(renderRequest, node.getMapExpression());
            WrappedCollection includeModel = collectionConverter.convert(mapValue).or(WrappedCollection.empty());

            renderRequest.getRenderContext().start(BlockContext.class, BlockContext.newContext());
            for (Node subNode : node.getNodes()) {
                renderNodeService.render(renderRequest, subNode);
            }

            Renderable renderable = renderResourceService.render(renderRequest, new RenderResourceRequest(newReference, false, !node.isInheritModel(), includeModel));

            renderRequest.getRenderContext().end(BlockContext.class);
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
