package org.jtwig.render.expression.calculator.enumerated;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IntegerDescendingOrderEnumerationListStrategy implements EnumerationListStrategy {
    @Override
    public Optional<List<Object>> enumerate(RenderRequest request, Object left, Object right) {
        Converter.Result<BigDecimal> leftValue = request.getEnvironment().getValueEnvironment().getNumberConverter().convert(left);
        Converter.Result<BigDecimal> rightValue = request.getEnvironment().getValueEnvironment().getNumberConverter().convert(right);

        if (leftValue.isDefined() && rightValue.isDefined()) {
            int start = leftValue.get().intValue();
            int end = rightValue.get().intValue();
            if (start >= end) {
                List<Object> result = new ArrayList<>();
                while (start > end) {
                    result.add(start);
                    start--;
                }
                result.add(start);
                return Optional.of(result);
            }
        }
        return Optional.absent();
    }
}
