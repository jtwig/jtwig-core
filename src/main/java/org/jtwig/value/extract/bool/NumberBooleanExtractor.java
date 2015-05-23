package org.jtwig.value.extract.bool;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.value.extract.Extractor;

import java.math.BigDecimal;

public class NumberBooleanExtractor implements Extractor<Boolean> {
    private final Extractor<BigDecimal> numberExtractor;

    public NumberBooleanExtractor(Extractor<BigDecimal> numberExtractor) {
        this.numberExtractor = numberExtractor;
    }

    @Override
    public Optional<Boolean> extract(Object value) {
        return numberExtractor.extract(value)
                .transform(new Function<BigDecimal, Boolean>() {
                    @Override
                    public Boolean apply(BigDecimal input) {
                        return input.compareTo(BigDecimal.ZERO) != 0;
                    }
                });
    }
}
