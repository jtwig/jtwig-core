package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.extract.Extractor;

import java.util.Arrays;
import java.util.List;

public class BooleanTypeExtractor implements Extractor<JtwigType> {
    @Override
    public Optional<JtwigType> extract(Object value) {
        List<Class> classes = Arrays.<Class>asList(
                Boolean.class,
                Boolean.TYPE
        );
        for (Class aClass : classes) {
            if (aClass.isAssignableFrom(value.getClass())) {
                return Optional.of(JtwigType.BOOLEAN);
            }
        }
        return Optional.absent();
    }
}
