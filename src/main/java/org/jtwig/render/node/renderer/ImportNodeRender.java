package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import org.jtwig.model.tree.ImportNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.StackedContext;
import org.jtwig.render.context.model.MacroAliasesContext;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.render.node.RenderNodeService;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.util.ErrorMessageFormatter;

public class ImportNodeRender implements NodeRender<ImportNode> {
    @Override
    public Renderable render(RenderRequest renderRequest, ImportNode node) {
        CalculateExpressionService calculateExpressionService = renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        ResourceResolver resourceResolver = renderRequest.getEnvironment().getResourceEnvironment().getResourceResolver();
        RenderNodeService renderNodeService = renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService();

        String macroIdentifier = node.getAliasIdentifier().getIdentifier();
        MacroDefinitionContext macroDefinitionContext = MacroDefinitionContext.newContext();
        renderRequest.getRenderContext().getMacroDefinitionContext().start(macroDefinitionContext);

        Object objectPath = calculateExpressionService.calculate(renderRequest, node.getImportExpression());
        String path = renderRequest.getEnvironment().getValueEnvironment().getStringConverter().convert(objectPath);
        Resource current = renderRequest.getRenderContext().getResourceContext().getCurrent();
        Optional<Resource> resolve = resourceResolver.resolve(renderRequest.getEnvironment(), current, path);

        if (resolve.isPresent()) {
            StackedContext<MacroAliasesContext> macroAliasesContext = renderRequest.getRenderContext().getMacroAliasesContext();
            if (macroAliasesContext.hasCurrent()) {
                macroAliasesContext.getCurrent().with(macroIdentifier, macroDefinitionContext);
            }
            macroAliasesContext.start(MacroAliasesContext.newContext());
            Node content = renderRequest.getEnvironment().getParser().parse(resolve.get());
            renderNodeService.render(renderRequest, content);
            renderRequest.getRenderContext().getValueContext().getCurrent().with(macroIdentifier, macroDefinitionContext);
            macroAliasesContext.end();
        } else {
            throw new ResourceNotFoundException(ErrorMessageFormatter.errorMessage(node.getPosition(), String.format("Resource '%s' not found", path)));
        }

        renderRequest.getRenderContext().getMacroDefinitionContext().end();

        return EmptyRenderable.instance();
    }
}
