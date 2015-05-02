package org.jtwig.model.tree;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

import java.util.Collection;

public class ExtendsNode extends Node {
    private final Expression extendsExpression;
    private final Collection<Node> nodes;

    public ExtendsNode(Position position, Expression extendsExpression, Collection<Node> nodes) {
        super(position);
        this.extendsExpression = extendsExpression;
        this.nodes = nodes;
    }

    public Expression getExtendsExpression() {
        return extendsExpression;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    @Override
    public Renderable render(RenderContext context) {
        String path = extendsExpression.calculate(context).asString();
        Optional<Resource> extendResource = context
                .configuration().resourceResolver()
                .resolve(context.currentResource().resource(), path);

        ResourceRenderResult resourceRenderResult = context
                .resourceRenderer()
                .render(extendResource.or(throwException(path)));

        context
                .currentResource()
                .merge(resourceRenderResult.context());

        for (Node node : nodes) {
            context.nodeRenderer().render(node);
        }

        return resourceRenderResult.renderable();
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
