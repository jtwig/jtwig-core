package org.jtwig.render.expression.calculator;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionArguments;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.expression.FunctionExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.util.ErrorMessageFormatter;

public class FunctionExpressionCalculator implements ExpressionCalculator<FunctionExpression> {
    private final FunctionArgumentsFactory functionArgumentsFactory;

    public FunctionExpressionCalculator(FunctionArgumentsFactory functionArgumentsFactory) {
        this.functionArgumentsFactory = functionArgumentsFactory;
    }

    @Override
    public Object calculate(RenderRequest request, FunctionExpression expression) {
        FunctionResolver functionResolver = request.getEnvironment().getFunctionResolver();

        FunctionArguments arguments = functionArgumentsFactory.create(request, expression.getArguments());
        Optional<Supplier<Object>> functionExecutor = functionResolver.resolve(request, expression.getPosition(), expression.getFunctionIdentifier(), arguments);

        if (functionExecutor.isPresent()) {
            return functionExecutor.get().get();
        } else {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(expression.getPosition(), String.format("Unable to resolve function '%s' with arguments %s", expression.getFunctionIdentifier(), arguments.getValues())));
        }
    }
}
