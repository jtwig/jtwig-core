package org.jtwig.value.compare;

import org.jtwig.render.RenderRequest;
import org.jtwig.value.convert.Converter;

import java.math.BigDecimal;

public class DefaultValueComparator implements ValueComparator {
    @Override
    public int compare(final RenderRequest renderRequest, Object left, Object right) {
        Converter<BigDecimal> numberConverter = renderRequest.getEnvironment().getValueEnvironment().getNumberConverter();
        Converter.Result<BigDecimal> leftNumber = numberConverter.convert(left);
        Converter.Result<BigDecimal> rightNumber = numberConverter.convert(right);

        if (leftNumber.isDefined()) {
            if (rightNumber.isDefined()) {
                return leftNumber.get().compareTo(rightNumber.get());
            }
        }

        return getString(renderRequest, left).compareTo(getString(renderRequest, right));
    }

    private String getString(RenderRequest renderRequest, Object value) {
        return renderRequest.getEnvironment().getValueEnvironment().getStringConverter().convert(value);
    }
}
