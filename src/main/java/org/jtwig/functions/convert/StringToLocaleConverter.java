package org.jtwig.functions.convert;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContextHolder;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;

import java.util.Locale;

public class StringToLocaleConverter implements Converter {
    @Override
    public Optional<Value> convert(Object value, Class type) {
        if (type.equals(Locale.class)) {
            if (value instanceof String) {
                Locale locale = RenderContextHolder.get().environment().renderConfiguration().localeResolver().resolve((String) value);
                return Optional.of(new Value(locale));
            }
        }
        return Optional.absent();
    }
}
