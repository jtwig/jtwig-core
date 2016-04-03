package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class MathOperationCalculator implements SimpleBinaryOperationCalculator {
    private final SimpleBinaryMathCalculator simpleBinaryMathCalculator;

    public MathOperationCalculator(SimpleBinaryMathCalculator simpleBinaryMathCalculator) {
        this.simpleBinaryMathCalculator = simpleBinaryMathCalculator;
    }

    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        Converter<BigDecimal> numberConverter = request.getEnvironment().getValueEnvironment().getNumberConverter();

        Converter.Result<BigDecimal> leftNumber = numberConverter.convert(left);
        if (!leftNumber.isDefined()) throw new CalculationException(errorMessage(position, "Left argument expected to be a number"));
        Converter.Result<BigDecimal> rightNumber = numberConverter.convert(right);
        if (!rightNumber.isDefined()) throw new CalculationException(errorMessage(position, "Right argument expected to be a number"));

        return simpleBinaryMathCalculator.calculate(request, leftNumber.get(), rightNumber.get());
    }
}
