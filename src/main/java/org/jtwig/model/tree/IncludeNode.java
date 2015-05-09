package org.jtwig.model.tree;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

public class IncludeNode extends Node {
    private final IncludeConfiguration configuration;

    public IncludeNode(Position position, IncludeConfiguration configuration) {
        super(position);
        this.configuration = configuration;
    }

    public Expression getMapExpression() {
        return configuration.getMap();
    }

    public boolean isInheritModel() {
        return configuration.isInheritModel();
    }

    public boolean isIgnoreMissing() {
        return configuration.isIgnoreMissing();
    }

    @Override
    public Renderable render(RenderContext context) {
        String path = configuration.getInclude().calculate(context).asString();
        Optional<Resource> resource = context.configuration()
                .resourceResolver()
                .resolve(context.currentResource().resource(), path);
        if (configuration.isIgnoreMissing() && !resource.isPresent()) {
            return EmptyRenderable.instance();
        } else {
            return context.resourceRenderer()
                    .inheritModel(configuration.isInheritModel())
                    .define(configuration.getMap().calculate(context).asMap())
                    .render(resource.or(throwException(path)))
                    .renderable();
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
