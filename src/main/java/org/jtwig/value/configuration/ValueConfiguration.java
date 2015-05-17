package org.jtwig.value.configuration;

import org.jtwig.value.extract.bool.BooleanExtractor;
import org.jtwig.value.extract.collection.CollectionExtractor;
import org.jtwig.value.extract.map.MapExtractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.extract.number.NumberExtractor;
import org.jtwig.value.extract.string.StringExtractor;
import org.jtwig.value.extract.type.TypeExtractor;
import org.jtwig.value.relational.RelationalComparator;

public interface ValueConfiguration {
    NumberExtractor numberExtractor();
    RelationalComparator equalComparator();
    RelationalComparator identicalComparator();
    RelationalComparator lowerComparator();
    RelationalComparator greaterComparator();
    TypeExtractor typeExtractor();
    BooleanExtractor booleanExtractor();
    CollectionExtractor collectionExtractor();
    MapExtractor mapExtractor();
    MapSelectionExtractor mapSelectionExtractor();
    StringExtractor stringExtractor();
}
