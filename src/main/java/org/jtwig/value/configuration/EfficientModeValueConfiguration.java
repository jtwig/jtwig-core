package org.jtwig.value.configuration;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.extract.CompositeExtractor;
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
import org.jtwig.value.relational.RelationalComparator;
import org.jtwig.value.relational.greater.ComparableGreaterComparator;
import org.jtwig.value.relational.greater.NumberGreaterComparator;
import org.jtwig.value.relational.greater.StringGreaterComparator;
import org.jtwig.value.relational.lower.ComparableLowerComparator;
import org.jtwig.value.relational.lower.NumberLowerComparator;
import org.jtwig.value.relational.lower.StringLowerComparator;

import java.util.Objects;

import static java.util.Arrays.asList;

public class EfficientModeValueConfiguration extends ValueConfiguration {
    private static final RelationalComparator EQUAL_COMPARATOR = new RelationalComparator() {
        @Override
        public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
            return Optional.of(Objects.equals(left.asObject(), right.asObject()));
        }
    };

    public EfficientModeValueConfiguration() {
        super(EQUAL_COMPARATOR, EQUAL_COMPARATOR, new CompositeRelationalComparator(asList(
                new NumberLowerComparator(),
                new StringLowerComparator(),
                new ComparableLowerComparator()
        )), new CompositeRelationalComparator(asList(
                new NumberGreaterComparator(),
                new StringGreaterComparator(),
                new ComparableGreaterComparator()
        )), new CompositeExtractor<>(asList(
                        new AnyTypeExtractor(),
                        new BooleanTypeExtractor(),
                        new StringTypeExtractor(),
                        new NumberTypeExtractor()
                )),
                new CompositeExtractor<>(asList(
                        new NullNumberExtractor(),
                        new BooleanNumberExtractor(),
                        new NativeNumberExtractor(),
                        new ObjectNumberExtractor()
                )),
                new CompositeExtractor<>(asList(
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
                )),
                new CompositeExtractor<>(asList(
                        new NullCollectionExtractor(),
                        new IterableCollectionExtractor(),
                        new ArrayCollectionExtractor(),
                        new MapCollectionExtractor()
                )),
                new CompositeExtractor<>(asList(
                        new NullMapExtractor(),
                        new NativeMapExtractor(),
                        new IterableMapExtractor(),
                        new ArrayMapExtractor()
                )),
                new CompositeExtractor<>(asList(
                        new NullStringExtractor(),
                        new BooleanStringExtractor(),
                        new NativeStringExtractor()
                )),
                new CompositeMapSelectionExtractor(asList(
                        new NativeMapSelectionExtractor(),
                        new ArrayMapSelectionExtractor()
                ))
        );
    }
}
