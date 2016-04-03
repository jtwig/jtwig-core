package org.jtwig.render.expression.calculator;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.util.ErrorMessageFormatter;

import java.util.ArrayList;
import java.util.List;

public class FunctionExpressionCalculator implements ExpressionCalculator<FunctionExpression> {
    @Override
    public Object calculate(RenderRequest request, FunctionExpression expression) {
        FunctionResolver functionResolver = request.getEnvironment().getFunctionResolver();

        List<Object> arguments = resolveArguments(request, expression.getArguments());
        Optional<Supplier> functionExecutor = functionResolver.resolve(request, expression.getPosition(), expression.getFunctionIdentifier(), arguments);

        if (functionExecutor.isPresent()) {
            return functionExecutor.get().get();
        } else {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("Unable to resolve function '%s' with arguments %s", expression.getFunctionIdentifier(), arguments)));
        }
    }

    private List<Object> resolveArguments(RenderRequest request, List<Expression> arguments) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        List<Object> result = new ArrayList<>();
        for (Expression argument : arguments) {
            result.add(calculateExpressionService.calculate(request, argument));
        }
        return result;
    }
}
