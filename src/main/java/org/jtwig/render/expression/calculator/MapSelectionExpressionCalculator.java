package org.jtwig.render.expression.calculator;

import org.jtwig.model.expression.MapSelectionExpression;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.expression.CalculateExpressionService;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

public class MapSelectionExpressionCalculator implements ExpressionCalculator<MapSelectionExpression> {
    @Override
    public Object calculate(RenderRequest request, MapSelectionExpression expression) {
        CalculateExpressionService calculateExpressionService = request.getEnvironment().getRenderEnvironment().getCalculateExpressionService();
        Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();

        Object mapExpressionValue = calculateExpressionService.calculate(request, expression.getMapExpression());
        WrappedCollection collection = collectionConverter.convert(mapExpressionValue).orThrow(expression.getPosition(), String.format("Cannot convert %s to a map", mapExpressionValue));

        Object calculate = calculateExpressionService.calculate(request, expression.getSelectValue());
        return collection.getValue(getString(request, calculate));
    }

    private String getString(RenderRequest request, Object input) {
        return request.getEnvironment().getValueEnvironment().getStringConverter().convert(input);
    }
}
