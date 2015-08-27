package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.extract.Extractor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class NumberTypeExtractor implements Extractor<JtwigType> {
    @Override
    public Optional<JtwigType> extract(Object value) {
        List<Class> classes = Arrays.<Class>asList(
                Number.class,
                BigDecimal.class,
                BigInteger.class,
                Integer.TYPE,
                Double.TYPE,
                Float.TYPE,
                Long.TYPE
        );
        for (Class aClass : classes) {
            if (aClass.isAssignableFrom(value.getClass())) {
                return Optional.of(JtwigType.NUMBER);
            }
        }
        return Optional.absent();
    }
}
