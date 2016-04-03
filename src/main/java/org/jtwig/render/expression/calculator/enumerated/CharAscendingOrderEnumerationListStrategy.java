package org.jtwig.render.expression.calculator.enumerated;

import com.google.common.base.Optional;
import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;

import java.util.ArrayList;
import java.util.List;

public class CharAscendingOrderEnumerationListStrategy implements EnumerationListStrategy {
    @Override
    public Optional<List<Object>> enumerate(RenderRequest request, Object left, Object right) {
        Converter.Result<Character> leftChar = request.getEnvironment().getValueEnvironment().getCharConverter().convert(left);
        Converter.Result<Character> rightChar = request.getEnvironment().getValueEnvironment().getCharConverter().convert(right);
        if (leftChar.isDefined() && rightChar.isDefined()) {
            Character start = leftChar.get();
            Character end = rightChar.get();
            if (start <= end) {
                List<Object> result = new ArrayList<>();
                while (start < end) {
                    result.add(start);
                    start++;
                }
                result.add(start);
                return Optional.of(result);
            }
        }
        return Optional.absent();
    }
}
