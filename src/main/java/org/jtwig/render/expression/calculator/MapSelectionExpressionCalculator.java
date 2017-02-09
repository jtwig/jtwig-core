package org.jtwig.render.expression.calculator;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.MapSelectionExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import static org.jtwig.util.ErrorMessageFormatter.errorMessage;

public class MapSelectionExpressionCalculator implements ExpressionCalculator<MapSelectionExpression> {
    @Override
    public Object calculate(RenderRequest request, MapSelectionExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();

        Object mapExpressionValue = calculateExpressionService.calculate(request, expression.getMapExpression());
        Converter.Result<WrappedCollection> wrappedCollectionResult = collectionConverter.convert(mapExpressionValue);

        if (!wrappedCollectionResult.isDefined()) {
            throw new CalculationException(errorMessage(expression.getPosition(), String.format("Cannot convert %s to a map", mapExpressionValue)));
        }

        WrappedCollection collection = wrappedCollectionResult.get();

        Object calculate = calculateExpressionService.calculate(request, expression.getSelectValue());
        return collection.getValue(getString(request, calculate));
    }

    private String getString(RenderRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(input);
    }
}
