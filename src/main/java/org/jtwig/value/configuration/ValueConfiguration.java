package org.jtwig.value.configuration;

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
    private final Extractor<BigDecimal> numberExtractor;
    private final Extractor<Boolean> booleanExtractor;
    private final Extractor<Collection<Object>> collectionExtractor;
    private final Extractor<Map> mapExtractor;
    private final Extractor<String> stringExtractor;
    private final MapSelectionExtractor mapSelectionExtractor;

    public ValueConfiguration(RelationalComparator equalComparator, RelationalComparator identicalComparator, RelationalComparator lowerComparator, RelationalComparator greaterComparator, Extractor<JtwigType> typeExtractor, Extractor<BigDecimal> numberExtractor, Extractor<Boolean> booleanExtractor, Extractor<Collection<Object>> collectionExtractor, Extractor<Map> mapExtractor, Extractor<String> stringExtractor, MapSelectionExtractor mapSelectionExtractor) {
        this.equalComparator = equalComparator;
        this.identicalComparator = identicalComparator;
        this.lowerComparator = lowerComparator;
        this.greaterComparator = greaterComparator;
        this.typeExtractor = typeExtractor;
        this.numberExtractor = numberExtractor;
        this.booleanExtractor = booleanExtractor;
        this.collectionExtractor = collectionExtractor;
        this.mapExtractor = mapExtractor;
        this.stringExtractor = stringExtractor;
        this.mapSelectionExtractor = mapSelectionExtractor;
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

    public Extractor<BigDecimal> getNumberExtractor() {
        return numberExtractor;
    }

    public Extractor<Boolean> getBooleanExtractor() {
        return booleanExtractor;
    }

    public Extractor<Collection<Object>> getCollectionExtractor() {
        return collectionExtractor;
    }

    public Extractor<Map> getMapExtractor() {
        return mapExtractor;
    }

    public Extractor<String> getStringExtractor() {
        return stringExtractor;
    }

    public MapSelectionExtractor getMapSelectionExtractor() {
        return mapSelectionExtractor;
    }
}
