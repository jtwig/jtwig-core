package org.jtwig.model.tree;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.EmptyRenderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;

public class ImportNode extends Node {
    private final Expression importExpression;
    private final VariableExpression aliasIdentifier;

    public ImportNode(Position position, Expression importExpression, VariableExpression aliasIdentifier) {
        super(position);
        this.importExpression = importExpression;
        this.aliasIdentifier = aliasIdentifier;
    }

    public Expression getImportExpression() {
        return importExpression;
    }

    public VariableExpression getAliasIdentifier() {
        return aliasIdentifier;
    }

    @Override
    public Renderable render(RenderContext context) {
        String path = importExpression.calculate(context).asString();
        Optional<Resource> resource = context
                .configuration().resourceResolver()
                .resolve(context.currentResource().resource(), path);
        ResourceRenderResult resourceRenderResult = context
                .resourceRenderer()
                .render(resource.or(throwException(path)));
        context.valueContext().add(aliasIdentifier.getIdentifier(), resourceRenderResult.context().macro());
        return EmptyRenderable.instance();
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
