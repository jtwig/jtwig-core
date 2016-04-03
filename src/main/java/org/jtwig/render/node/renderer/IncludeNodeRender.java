package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.model.tree.IncludeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.RenderResourceRequest;
import org.jtwig.render.RenderResourceService;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class IncludeNodeRender implements NodeRender<IncludeNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, IncludeNode node) {
        CalculateExpressionService calculateExpressionService = renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        ResourceResolver resourceResolver = renderRequest.getEnvironment().getResourceEnvironment().getResourceResolver();

        Object path = calculateExpressionService.calculate(renderRequest, node.getResourceExpression());
        Resource current = renderRequest.getRenderContext().getResourceContext().getCurrent();
        String relativePath = renderRequest.getEnvironment().getValueEnvironment().getStringConverter().convert(path);
        Optional<Resource> resource = resourceResolver.resolve(renderRequest.getEnvironment(), current, relativePath);
        if (resource.isPresent()) {
            Converter<WrappedCollection> mapConverter = renderRequest.getEnvironment().getValueEnvironment().getCollectionConverter();
            RenderResourceService renderResourceService = renderRequest.getEnvironment().getRenderEnvironment().getRenderResourceService();

            Object mapValue = calculateExpressionService.calculate(renderRequest, node.getMapExpression());
            WrappedCollection includeModel = mapConverter.convert(mapValue).or(WrappedCollection.empty());
            return renderResourceService.render(renderRequest, new RenderResourceRequest(resource.get(),
                    true, !node.isInheritModel(),
                    includeModel
            ));
        } else {
            if (node.isIgnoreMissing()) {
                return EmptyRenderable.instance();
            } else {
                throw new ResourceNotFoundException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Resource '%s' not found", path)));
            }
        }
    }
}
