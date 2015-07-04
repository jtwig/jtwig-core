package org.jtwig.value.extract;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;

public class ExtractToConverter implements Converter {
    private final Extractor extractor;
    private final Class type;

    public ExtractToConverter(Extractor extractor, Class type) {
        this.extractor = extractor;
        this.type = type;
    }

    @Override
    public Optional<Value> convert(Object input, Class to) {
        if (to.isAssignableFrom(type)) {
            return extractor.extract(input).transform(new Function() {
                @Override
                public Value apply(Object input) {
                    return new Value(input);
                }
            });
        }
        return Optional.absent();
    }
}
