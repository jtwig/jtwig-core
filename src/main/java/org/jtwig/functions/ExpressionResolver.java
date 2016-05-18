package org.jtwig.functions;

import com.google.common.base.Function;
import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;

public class ExpressionResolver implements Function<Expression, Object> {
    private final RenderRequest renderRequest;

    public ExpressionResolver(RenderRequest renderRequest) {
        this.renderRequest = renderRequest;
    }

    @Override
    public Object apply(Expression input) {
        return renderRequest.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(renderRequest, input);
    }
}
