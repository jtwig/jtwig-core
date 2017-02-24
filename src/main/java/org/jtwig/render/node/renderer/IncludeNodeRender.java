package org.jtwig.render.node.renderer;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.IncludeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.RenderResourceRequest;
import org.jtwig.render.RenderResourceService;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class IncludeNodeRender implements NodeRender<IncludeNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, IncludeNode node) {
        Environment environment = renderRequest.getEnvironment();
        CalculateExpressionService calculateExpressionService = environment.getRenderEnvironment().getCalculateExpressionService();
        ResourceService resourceService = environment.getResourceEnvironment().getResourceService();

        Object path = calculateExpressionService.calculate(renderRequest, node.getResourceExpression());
        ResourceReference current = renderRequest.getRenderContext().getResourceContext().getCurrent().getItem();
        String relativePath = environment.getValueEnvironment().getStringConverter().convert(path);
        ResourceReference newReference = resourceService.resolve(current, relativePath);
        ResourceMetadata resourceMetadata = resourceService.loadMetadata(newReference);

        if (resourceMetadata.exists()) {
            Converter<WrappedCollection> mapConverter = environment.getValueEnvironment().getCollectionConverter();
            RenderResourceService renderResourceService = environment.getRenderEnvironment().getRenderResourceService();

            Object mapValue = calculateExpressionService.calculate(renderRequest, node.getMapExpression());
            WrappedCollection includeModel = mapConverter.convert(mapValue).or(WrappedCollection.empty());
            return renderResourceService.render(renderRequest, new RenderResourceRequest(newReference,
                    true, !node.isInheritModel(),
                    includeModel
            ));
        } else {
            if (node.isIgnoreMissing()) {
                return EmptyRenderable.instance();
            } else {
                throw new ResourceNotFoundException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Resource '%s' (resolved to '%s') not found", path, newReference)));
            }
        }
    }
}
