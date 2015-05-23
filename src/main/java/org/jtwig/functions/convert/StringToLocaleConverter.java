package org.jtwig.functions.convert;

import com.google.common.base.Optional;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;

import java.util.Locale;

public class StringToLocaleConverter implements Converter {
    @Override
    public Optional<Value> convert(Object o, Class aClass) {
        if (aClass.equals(Locale.class)) {
            if (o instanceof String) {
                return Optional.of(new Value(Locale.forLanguageTag((String)o)));
            }
        }
        return Optional.absent();
    }
}
