package org.jtwig.value.converter;

import com.google.common.base.Optional;
import org.jtwig.reflection.model.Value;

import java.util.Collection;

public class CompositeConverter implements Converter {
    private final Collection<Converter> converterCollection;

    public CompositeConverter(Collection<Converter> converterCollection) {
        this.converterCollection = converterCollection;
    }

    @Override
    public Optional<Value> convert(Object input, Class to) {
        for (Converter converter : converterCollection) {
            Optional<Value> result = converter.convert(input, to);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.absent();
    }
}
