package org.jtwig.model.tree;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmbedNode extends Node {
    private final Collection<OverrideBlockNode> nodes;
    private final IncludeConfiguration includeConfiguration;

    public EmbedNode(Position position, Collection<OverrideBlockNode> nodes, IncludeConfiguration includeConfiguration) {
        super(position);
        this.nodes = nodes;
        this.includeConfiguration = includeConfiguration;
    }

    public Collection<OverrideBlockNode> getNodes() {
        return nodes;
    }

    public Expression getMapExpression() {
        return includeConfiguration.getMap();
    }

    public boolean isInheritModel() {
        return includeConfiguration.isInheritModel();
    }

    public boolean isIgnoreMissing() {
        return includeConfiguration.isIgnoreMissing();
    }

    @Override
    public Renderable render(RenderContext context) {
        String path = includeConfiguration.getInclude().calculate(context).asString();
        Optional<Resource> resource = context.environment()
                .resourceResolver()
                .resolve(context.currentResource().resource(), path);

        if (includeConfiguration.isIgnoreMissing()) {
            if (!resource.isPresent()) {
                return EmptyRenderable.instance();
            }
        }

        Map<String, Renderable> blocks = new HashMap<>();

        for (OverrideBlockNode node : nodes) {
            blocks.put(node.getIdentifier(), node.getContent().render(context));
        }

        ResourceRenderResult renderResult = context.resourceRenderer()
                .inheritModel(includeConfiguration.isInheritModel())
                .define(includeConfiguration.getMap().calculate(context).asMap())
                .blocks(blocks)
                .render(resource.or(throwException(path)));

        return renderResult.renderable();
    }

    private Supplier<Resource> throwException (final String path) {
        return new Supplier<Resource>() {
            @Override
            public Resource get() {
                throw new ResourceNotFoundException(String.format("%s: Resource '%s' not found", getPosition(), path));
            }
        };
    }

}
