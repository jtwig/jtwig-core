package org.jtwig.render.node.renderer;

import org.jtwig.environment.Environment;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.ExtendsNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.RenderResourceRequest;
import org.jtwig.render.RenderResourceService;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.WrappedCollection;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class ExtendsNodeRender implements NodeRender<ExtendsNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, ExtendsNode node) {
        Environment environment = renderRequest.getEnvironment();
        CalculateExpressionService calculateExpressionService = renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();
        RenderResourceService renderResourceService = renderRequest.getEnvironment().getRenderEnvironment().getRenderResourceService();
        ResourceService resourceService = renderRequest.getEnvironment().getResourceEnvironment().getResourceService();

        Expression extendsExpression = node.getExtendsExpression();
        Object path = calculateExpressionService.calculate(renderRequest, extendsExpression);
        ResourceReference current = renderRequest.getRenderContext().getCurrent(ResourceReference.class);

        ResourceReference newReference = resourceService.resolve(current, path, environment.getValueEnvironment());
        ResourceMetadata resourceMetadata = resourceService.loadMetadata(newReference);

        if (!resourceMetadata.exists()) {
            throw new ResourceNotFoundException(errorMessage(node.getPosition(), String.format("Resource '%s' not found", environment.getValueEnvironment().getStringConverter().convert(path))));
        } else {
            for (Node subNode : node.getNodes()) {
                renderNodeService.render(renderRequest, subNode);
            }

            return renderResourceService.render(renderRequest, new RenderResourceRequest(newReference, false, false, WrappedCollection.empty()));
        }
    }
}
