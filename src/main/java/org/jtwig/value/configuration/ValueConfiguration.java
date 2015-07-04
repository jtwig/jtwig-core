package org.jtwig.value.configuration;

import org.jtwig.reflection.convert.Converter;
import org.jtwig.value.JtwigType;
import org.jtwig.value.extract.Extractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.relational.RelationalComparator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class ValueConfiguration {
    private final RelationalComparator equalComparator;
    private final RelationalComparator identicalComparator;
    private final RelationalComparator lowerComparator;
    private final RelationalComparator greaterComparator;
    private final Extractor<JtwigType> typeExtractor;
    private final MapSelectionExtractor mapSelectionExtractor;
    private final Collection<Converter> converter;

    public ValueConfiguration(RelationalComparator equalComparator, RelationalComparator identicalComparator, RelationalComparator lowerComparator, RelationalComparator greaterComparator, Extractor<JtwigType> typeExtractor, MapSelectionExtractor mapSelectionExtractor, Collection<Converter> converter) {
        this.equalComparator = equalComparator;
        this.identicalComparator = identicalComparator;
        this.lowerComparator = lowerComparator;
        this.greaterComparator = greaterComparator;
        this.typeExtractor = typeExtractor;
        this.mapSelectionExtractor = mapSelectionExtractor;
        this.converter = converter;
    }

    public RelationalComparator getEqualComparator() {
        return equalComparator;
    }

    public RelationalComparator getIdenticalComparator() {
        return identicalComparator;
    }

    public RelationalComparator getLowerComparator() {
        return lowerComparator;
    }

    public RelationalComparator getGreaterComparator() {
        return greaterComparator;
    }

    public Extractor<JtwigType> getTypeExtractor() {
        return typeExtractor;
    }

    public MapSelectionExtractor getMapSelectionExtractor() {
        return mapSelectionExtractor;
    }

    public Collection<Converter> getConverter() {
        return converter;
    }
}
