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

public class EmbedNode extends Node {
    private final Collection<Node> nodes;
    private final IncludeConfiguration includeConfiguration;

    public EmbedNode(Position position, Collection<Node> nodes, IncludeConfiguration includeConfiguration) {
        super(position);
        this.nodes = nodes;
        this.includeConfiguration = includeConfiguration;
    }

    public Collection<Node> getNodes() {
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
        Optional<Resource> resource = context.configuration()
                .resourceResolver()
                .resolve(context.currentResource().resource(), path);
        if (includeConfiguration.isIgnoreMissing() && !resource.isPresent()) {
            return EmptyRenderable.instance();
        } else {

            ResourceRenderResult renderResult = context.resourceRenderer()
                    .inheritModel(includeConfiguration.isInheritModel())
                    .define(includeConfiguration.getMap().calculate(context).asMap())
                    .render(resource.or(throwException(path)));

            // Get and merge blocks
            context.currentResource()
                    .merge(renderResult.context());

            for (Node node : nodes) {
                context.nodeRenderer().render(node);
            }

            return renderResult.renderable();
        }
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
