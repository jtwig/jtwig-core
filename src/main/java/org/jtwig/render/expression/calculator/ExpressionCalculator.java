package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.Expression;
import org.jtwig.render.RenderRequest;

public interface ExpressionCalculator<T extends Expression> {
    Object calculate (RenderRequest request, T expression);
}
