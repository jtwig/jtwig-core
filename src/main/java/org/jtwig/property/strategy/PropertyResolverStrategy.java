package org.jtwig.property.strategy;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.render.RenderRequest;

public interface PropertyResolverStrategy {
    /**
     * Preconditions:
     *
     * request.leftValue != null && request.leftValue != Undefined.Undefined
     */
    Optional<PropertyResolver> select (Request request);

    class Request extends RenderRequest {
        private final Object leftValue;
        private final Expression rightExpression;

        public Request(RenderRequest request, Object leftValue, Expression rightExpression) {
            super(request.getRenderContext(), request.getEnvironment());
            this.leftValue = leftValue;
            this.rightExpression = rightExpression;
        }

        public Object getLeftValue() {
            return leftValue;
        }

        public Expression getRightExpression() {
            return rightExpression;
        }
    }
}
