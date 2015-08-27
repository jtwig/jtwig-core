package org.jtwig.model.tree;

import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.model.expression.InjectableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.render.impl.StringRenderable;

public class FilterNode extends ContentNode {
    private final InjectableExpression filterExpression;

    public FilterNode(Position position, Node content, InjectableExpression filterExpression) {
        super(position, content);
        this.filterExpression = filterExpression;
    }

    @Override
    public Renderable render(RenderContext context) {
        Renderable renderable = super.render(context);
        String content = renderable.appendTo(new StringBuilderRenderResult()).content();
        ConstantExpression expression = new ConstantExpression(filterExpression.getPosition(), content);
        String modifiedContent = filterExpression.inject(expression)
                .calculate(context)
                .asString();
        return new StringRenderable(modifiedContent, EscapeMode.NONE);
    }
}
