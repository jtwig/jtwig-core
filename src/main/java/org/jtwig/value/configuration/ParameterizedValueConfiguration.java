package org.jtwig.value.configuration;

import org.jtwig.value.extract.bool.BooleanExtractor;
import org.jtwig.value.extract.collection.CollectionExtractor;
import org.jtwig.value.extract.map.MapExtractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.extract.number.NumberExtractor;
import org.jtwig.value.extract.string.StringExtractor;
import org.jtwig.value.extract.type.TypeExtractor;
import org.jtwig.value.relational.RelationalComparator;

public class ParameterizedValueConfiguration implements ValueConfiguration {
    private final TypeExtractor typeExtractor;
    private final NumberExtractor numberExtractor;
    private final BooleanExtractor booleanExtractor;
    private final CollectionExtractor collectionExtractor;
    private final MapExtractor mapExtractor;
    private final RelationalComparator equalComparator;
    private final RelationalComparator identicalComparator;
    private final RelationalComparator lowerComparator;
    private final RelationalComparator greaterComparator;
    private final MapSelectionExtractor mapSelectionExtractor;
    private final StringExtractor stringExtractor;

    public ParameterizedValueConfiguration(TypeExtractor typeExtractor, NumberExtractor numberExtractor, BooleanExtractor booleanExtractor, CollectionExtractor collectionExtractor, MapExtractor mapExtractor, RelationalComparator equalComparator, RelationalComparator identicalComparator, RelationalComparator lowerComparator, RelationalComparator greaterComparator, MapSelectionExtractor mapSelectionExtractor, StringExtractor stringExtractor) {
        this.typeExtractor = typeExtractor;
        this.numberExtractor = numberExtractor;
        this.booleanExtractor = booleanExtractor;
        this.collectionExtractor = collectionExtractor;
        this.mapExtractor = mapExtractor;
        this.equalComparator = equalComparator;
        this.identicalComparator = identicalComparator;
        this.lowerComparator = lowerComparator;
        this.greaterComparator = greaterComparator;
        this.mapSelectionExtractor = mapSelectionExtractor;
        this.stringExtractor = stringExtractor;
    }

    @Override
    public NumberExtractor numberExtractor() {
        return numberExtractor;
    }

    @Override
    public RelationalComparator equalComparator() {
        return equalComparator;
    }

    @Override
    public RelationalComparator identicalComparator() {
        return identicalComparator;
    }

    @Override
    public RelationalComparator lowerComparator() {
        return lowerComparator;
    }

    @Override
    public TypeExtractor typeExtractor() {
        return typeExtractor;
    }

    @Override
    public BooleanExtractor booleanExtractor() {
        return booleanExtractor;
    }

    @Override
    public CollectionExtractor collectionExtractor() {
        return collectionExtractor;
    }

    @Override
    public MapExtractor mapExtractor() {
        return mapExtractor;
    }

    @Override
    public MapSelectionExtractor mapSelectionExtractor() {
        return mapSelectionExtractor;
    }

    @Override
    public StringExtractor stringExtractor() {
        return stringExtractor;
    }

    @Override
    public RelationalComparator greaterComparator() {
        return greaterComparator;
    }
}
