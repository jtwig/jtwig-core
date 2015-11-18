package org.jtwig.value.configuration;

import org.jtwig.value.converter.CompositeConverter;
import org.jtwig.value.converter.Converter;
import org.jtwig.value.converter.IdentityConverter;
import org.jtwig.value.converter.NullConverter;
import org.jtwig.value.converter.bool.*;
import org.jtwig.value.converter.collection.ArrayCollectionConverter;
import org.jtwig.value.converter.collection.IterableCollectionConverter;
import org.jtwig.value.converter.collection.MapCollectionConverter;
import org.jtwig.value.converter.collection.NullCollectionConverter;
import org.jtwig.value.converter.map.ArrayMapConverter;
import org.jtwig.value.converter.map.IterableMapConverter;
import org.jtwig.value.converter.map.NativeMapConverter;
import org.jtwig.value.converter.map.NullMapConverter;
import org.jtwig.value.converter.number.BooleanNumberConverter;
import org.jtwig.value.converter.number.NativeNumberConverter;
import org.jtwig.value.converter.number.NullNumberConverter;
import org.jtwig.value.converter.number.ObjectNumberConverter;
import org.jtwig.value.converter.string.BooleanStringConverter;
import org.jtwig.value.converter.string.NativeStringConverter;
import org.jtwig.value.converter.string.NullStringConverter;
import org.jtwig.value.extract.CompositeExtractor;
import org.jtwig.value.extract.map.selection.ArrayMapSelectionExtractor;
import org.jtwig.value.extract.map.selection.CompositeMapSelectionExtractor;
import org.jtwig.value.extract.map.selection.NativeMapSelectionExtractor;
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

import java.math.MathContext;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class DefaultValueConfiguration extends ValueConfiguration {
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
        super(MathContext.DECIMAL128, EQUAL_COMPARATOR,
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
                Arrays.asList(
                        new IdentityConverter(),
                        // Numbers
                        new NullNumberConverter(),
                        new BooleanNumberConverter(),
                        new NativeNumberConverter(),
                        new ObjectNumberConverter(),
                        // Booleans
                        new NullBooleanConverter(),
                        new NativeBooleanConverter(),
                        new IterableBooleanConverter(),
                        new ArrayBooleanConverter(),
                        new MapBooleanConverter(),
                        new NumberBooleanConverter(new CompositeConverter(Arrays.<Converter>asList(
                                new NullNumberConverter(),
                                new BooleanNumberConverter(),
                                new NativeNumberConverter(),
                                new ObjectNumberConverter()
                        ))),
                        new StringBooleanConverter(),
                        // Collections
                        new NullCollectionConverter(),
                        new IterableCollectionConverter(),
                        new ArrayCollectionConverter(),
                        new MapCollectionConverter(),
                        // Maps
                        new NullMapConverter(),
                        new NativeMapConverter(),
                        new IterableMapConverter(),
                        new ArrayMapConverter(),
                        // Strings
                        new NullStringConverter(),
                        new BooleanStringConverter(),
                        new NativeStringConverter(),

                        new NullConverter()
                ));
    }
}