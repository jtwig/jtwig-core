package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
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
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.value.WrappedCollection;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class ExtendsNodeRender implements NodeRender<ExtendsNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, ExtendsNode node) {
        CalculateExpressionService calculateExpressionService = renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();
        RenderResourceService renderResourceService = renderRequest.getEnvironment().getRenderEnvironment().getRenderResourceService();
        ResourceResolver resourceResolver = renderRequest.getEnvironment().getResourceEnvironment().getResourceResolver();

        Expression extendsExpression = node.getExtendsExpression();
        Object objectPath = calculateExpressionService.calculate(renderRequest, extendsExpression);
        String path = renderRequest.getEnvironment().getValueEnvironment().getStringConverter().convert(objectPath);
        Environment environment = renderRequest.getEnvironment();
        Resource current = renderRequest.getRenderContext().getResourceContext().getCurrent();
        Optional<Resource> optionalExtendResource = resourceResolver.resolve(environment, current, path);

        if (!optionalExtendResource.isPresent()) {
            throw new ResourceNotFoundException(errorMessage(node.getPosition(), String.format("Resource '%s' not found", path)));
        } else {
            Resource extendResource = optionalExtendResource.get();

            for (Node subNode : node.getNodes()) {
                renderNodeService.render(renderRequest, subNode);
            }

            return renderResourceService.render(renderRequest, new RenderResourceRequest(extendResource, false, false, WrappedCollection.empty()));
        }
    }
}
