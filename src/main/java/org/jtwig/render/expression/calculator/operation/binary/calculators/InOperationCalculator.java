package org.jtwig.render.expression.calculator.operation.binary.calculators;

import org.jtwig.model.position.Position;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;

import java.util.Iterator;
import java.util.Map;

public class InOperationCalculator implements SimpleBinaryOperationCalculator {
    @Override
    public Object calculate(RenderRequest request, Position position, Object left, Object right) {
        Converter<WrappedCollection> collectionConverter = request.getEnvironment().getValueEnvironment().getCollectionConverter();
        WrappedCollection wrappedCollection = collectionConverter.convert(right).or(WrappedCollection.singleton(right));
        Iterator<Map.Entry<String, Object>> iterator = wrappedCollection.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();

            if (request.getEnvironment().getValueEnvironment().getValueComparator().compare(request, left, next.getValue()) == 0) {
                return true;
            }
        }

        return false;
    }
}
