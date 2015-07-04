package org.jtwig.value.configuration;

import org.jtwig.reflection.convert.Converter;
import org.jtwig.value.extract.CompositeExtractor;
import org.jtwig.value.extract.ExtractToConverter;
import org.jtwig.value.extract.bool.*;
import org.jtwig.value.extract.collection.ArrayCollectionExtractor;
import org.jtwig.value.extract.collection.IterableCollectionExtractor;
import org.jtwig.value.extract.collection.MapCollectionExtractor;
import org.jtwig.value.extract.collection.NullCollectionExtractor;
import org.jtwig.value.extract.map.ArrayMapExtractor;
import org.jtwig.value.extract.map.IterableMapExtractor;
import org.jtwig.value.extract.map.NativeMapExtractor;
import org.jtwig.value.extract.map.NullMapExtractor;
import org.jtwig.value.extract.map.selection.ArrayMapSelectionExtractor;
import org.jtwig.value.extract.map.selection.CompositeMapSelectionExtractor;
import org.jtwig.value.extract.map.selection.NativeMapSelectionExtractor;
import org.jtwig.value.extract.number.BooleanNumberExtractor;
import org.jtwig.value.extract.number.NativeNumberExtractor;
import org.jtwig.value.extract.number.NullNumberExtractor;
import org.jtwig.value.extract.number.ObjectNumberExtractor;
import org.jtwig.value.extract.string.BooleanStringExtractor;
import org.jtwig.value.extract.string.NativeStringExtractor;
import org.jtwig.value.extract.string.NullStringExtractor;
import org.jtwig.value.extract.type.AnyTypeExtractor;
import org.jtwig.value.extract.type.BooleanTypeExtractor;
import org.jtwig.value.extract.type.NumberTypeExtractor;
import org.jtwig.value.extract.type.StringTypeExtractor;
import org.jtwig.value.relational.CompositeRelationalComparator;
import org.jtwig.value.relational.NotComparator;
import org.jtwig.value.relational.RelationalComparator;
import org.jtwig.value.relational.equal.*;
import org.jtwig.value.relational.identical.SameTypeComparator;
import org.jtwig.value.relational.lower.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static java.util.Arrays.asList;

public class DefaultValueConfiguration extends ValueConfiguration {
    public static final CompositeExtractor<java.math.BigDecimal> NUMBER_EXTRACTOR = new CompositeExtractor<>(asList(
            new NullNumberExtractor(),
            new BooleanNumberExtractor(),
            new NativeNumberExtractor(),
            new ObjectNumberExtractor()
    ));
    private static final CompositeRelationalComparator EQUAL_COMPARATOR = new CompositeRelationalComparator(asList(
            new BooleanEqualComparator(),
            new StringEqualComparator(),
            new NullAndStringEqualComparator(),
            new NumberStringEqualComparator(),
            new NumberEqualComparator()
    ));
    private static final CompositeRelationalComparator LOWER_COMPARATOR = new CompositeRelationalComparator(asList(
            new BooleanAndNumberLowerComparator(),
            new BooleanAndStringLowerComparator(),
            new NumberAndStringLowerComparator(),
            new NullAndNumberLowerComparator(),
            new NullAndStringLowerComparator(),
            new StringLowerComparator(),
            new NumberLowerComparator()
    ));

    public DefaultValueConfiguration() {
        super(EQUAL_COMPARATOR,
                new SameTypeComparator(EQUAL_COMPARATOR),
                LOWER_COMPARATOR,
                new CompositeRelationalComparator(Arrays.<RelationalComparator>asList(
                        new NotComparator(LOWER_COMPARATOR),
                        new NotComparator(EQUAL_COMPARATOR)
                )),
                new CompositeExtractor<>(asList(
                        new AnyTypeExtractor(),
                        new BooleanTypeExtractor(),
                        new StringTypeExtractor(),
                        new NumberTypeExtractor()
                )),
                new CompositeMapSelectionExtractor(asList(
                        new NativeMapSelectionExtractor(),
                        new ArrayMapSelectionExtractor()
                )),
                Arrays.<Converter>asList(
                        new ExtractToConverter(NUMBER_EXTRACTOR, BigDecimal.class),
                        new ExtractToConverter(new CompositeExtractor<>(asList(
                                new NullBooleanExtractor(),
                                new NativeBooleanExtractor(),
                                new IterableBooleanExtractor(),
                                new ArrayBooleanExtractor(),
                                new MapBooleanExtractor(),
                                new NumberBooleanExtractor(new CompositeExtractor<>(asList(
                                        new NullNumberExtractor(),
                                        new BooleanNumberExtractor(),
                                        new NativeNumberExtractor(),
                                        new ObjectNumberExtractor()
                                ))),
                                new StringBooleanExtractor()
                        )), Boolean.class),
                        new ExtractToConverter(new CompositeExtractor<>(asList(
                                new NullCollectionExtractor(),
                                new IterableCollectionExtractor(),
                                new ArrayCollectionExtractor(),
                                new MapCollectionExtractor()
                        )), Collection.class),
                        new ExtractToConverter(new CompositeExtractor<>(asList(
                                new NullMapExtractor(),
                                new NativeMapExtractor(),
                                new IterableMapExtractor(),
                                new ArrayMapExtractor()
                        )), Map.class),
                        new ExtractToConverter(new CompositeExtractor<>(asList(
                                new NullStringExtractor(),
                                new BooleanStringExtractor(),
                                new NativeStringExtractor()
                        )), String.class)
                ));
    }
}