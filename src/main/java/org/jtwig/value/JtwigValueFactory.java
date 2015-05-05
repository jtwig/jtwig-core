package org.jtwig.value;

import org.jtwig.value.extract.bool.*;
import org.jtwig.value.extract.collection.*;
import org.jtwig.value.extract.map.*;
import org.jtwig.value.extract.number.*;
import org.jtwig.value.extract.type.*;

import static java.util.Arrays.asList;

public class JtwigValueFactory {
    private static final CompositeNumberExtractor NUMBER_EXTRACTOR = new CompositeNumberExtractor(asList(
            new NullNumberExtractor(),
            new BooleanNumberExtractor(),
            new NativeNumberExtractor(),
            new ObjectNumberExtractor()
    ));
    private static final CompositeBooleanExtractor BOOLEAN_EXTRACTOR = new CompositeBooleanExtractor(asList(
            new NullBooleanExtractor(),
            new NativeBooleanExtractor(),
            new IterableBooleanExtractor(),
            new ArrayBooleanExtractor(),
            new MapBooleanExtractor(),
            new NumberBooleanExtractor(NUMBER_EXTRACTOR),
            new StringBooleanExtractor()
    ));
    private static final CompositeTypeExtractor TYPE_EXTRACTOR = new CompositeTypeExtractor(asList(
            new AnyTypeExtractor(),
            new BooleanTypeExtractor(),
            new StringTypeExtractor(),
            new NumberTypeExtractor()
    ));
    private static final CompositeCollectionExtractor COLLECTION_EXTRACTOR = new CompositeCollectionExtractor(asList(
            new NullCollectionExtractor(),
            new IterableCollectionExtractor(),
            new ArrayCollectionExtractor(),
            new MapCollectionExtractor()
    ));
    private static final CompositeMapExtractor MAP_EXTRACTOR = new CompositeMapExtractor(asList(
            new NullMapExtractor(),
            new NativeMapExtractor(),
            new IterableMapExtractor(),
            new ArrayMapExtractor()
    ));

    public static JtwigValue create (Object value) {
        return new JtwigValue(value, TYPE_EXTRACTOR, NUMBER_EXTRACTOR, COLLECTION_EXTRACTOR, MAP_EXTRACTOR, BOOLEAN_EXTRACTOR);
    }

    public static JtwigValue empty () {
        return create(Undefined.UNDEFINED);
    }
}
