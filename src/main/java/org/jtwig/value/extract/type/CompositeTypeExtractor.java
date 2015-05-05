package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;

import java.util.Collection;

public class CompositeTypeExtractor implements TypeExtractor {
    private final Collection<TypeExtractor> extractors;

    public CompositeTypeExtractor(Collection<TypeExtractor> extractors) {
        this.extractors = extractors;
    }

    @Override
    public Optional<JtwigType> extract(Object value) {
        for (TypeExtractor extractor : extractors) {
            Optional<JtwigType> extract = extractor.extract(value);
            if (extract.isPresent()) {
                return extract;
            }
        }
        return Optional.absent();
    }
}
