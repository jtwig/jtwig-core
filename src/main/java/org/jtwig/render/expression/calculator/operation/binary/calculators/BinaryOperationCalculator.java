package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;

public interface BinaryOperationCalculator {
    Object calculate(Request request);

    class Request extends RenderRequest {
        private final Position position;
        private final Expression leftOperand;
        private final Expression rightOperand;

        public Request(RenderRequest request, Position position, Expression leftOperand, Expression rightOperand) {
            super(request.getRenderContext(), request.getEnvironment());
            this.position = position;
            this.leftOperand = leftOperand;
            this.rightOperand = rightOperand;
        }

        public Position getPosition() {
            return position;
        }

        public Expression getLeftOperand() {
            return leftOperand;
        }

        public Expression getRightOperand() {
            return rightOperand;
        }
    }
}
