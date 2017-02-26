package org.jtwig.render.node.renderer;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.ImportNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.context.ValueContext;

public class ImportNodeRender implements NodeRender<ImportNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, ImportNode node) {
        Environment environment = renderRequest.getEnvironment();
        CalculateExpressionService calculateExpressionService = environment.getRenderEnvironment().getCalculateExpressionService();
        ResourceService resourceService = environment.getResourceEnvironment().getResourceService();
        RenderNodeService renderNodeService = environment.getRenderEnvironment().getRenderNodeService();

        String macroIdentifier = node.getAliasIdentifier().getIdentifier();
        MacroDefinitionContext macroDefinitionContext = MacroDefinitionContext.newContext();
        renderRequest.getRenderContext().start(MacroDefinitionContext.class, macroDefinitionContext);

        Object objectPath = calculateExpressionService.calculate(renderRequest, node.getImportExpression());
        String path = environment.getValueEnvironment().getStringConverter().convert(objectPath);
        ResourceReference current = renderRequest.getRenderContext().getCurrent(ResourceReference.class);
        ResourceReference newReference = resourceService.resolve(current, path);
        ResourceMetadata resourceMetadata = resourceService.loadMetadata(newReference);

        if (resourceMetadata.exists()) {
            if (renderRequest.getRenderContext().hasCurrent(MacroAliasesContext.class)) {
                renderRequest.getRenderContext().getCurrent(MacroAliasesContext.class).with(macroIdentifier, macroDefinitionContext);
            }
            renderRequest.getRenderContext().start(MacroAliasesContext.class, MacroAliasesContext.newContext());
            Node content = environment.getParser().parse(environment, newReference);
            renderNodeService.render(renderRequest, content);
            renderRequest.getRenderContext().getCurrent(ValueContext.class).with(macroIdentifier, macroDefinitionContext);
            renderRequest.getRenderContext().end(MacroAliasesContext.class);
        } else {
            throw new ResourceNotFoundException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Resource '%s' not found", path)));
        }

        renderRequest.getRenderContext().end(MacroDefinitionContext.class);

        return EmptyRenderable.instance();
    }
}
