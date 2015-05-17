package org.jtwig.value.configuration;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.extract.bool.*;
import org.jtwig.value.extract.collection.*;
import org.jtwig.value.extract.map.*;
import org.jtwig.value.extract.map.selection.ArrayMapSelectionExtractor;
import org.jtwig.value.extract.map.selection.CompositeMapSelectionExtractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.extract.map.selection.NativeMapSelectionExtractor;
import org.jtwig.value.extract.number.*;
import org.jtwig.value.extract.type.*;
import org.jtwig.value.relational.CompositeRelationalComparator;
import org.jtwig.value.relational.NotComparator;
import org.jtwig.value.relational.RelationalComparator;
import org.jtwig.value.relational.equal.*;
import org.jtwig.value.relational.greater.ComparableGreaterComparator;
import org.jtwig.value.relational.greater.NumberGreaterComparator;
import org.jtwig.value.relational.greater.StringGreaterComparator;
import org.jtwig.value.relational.identical.SameTypeComparator;
import org.jtwig.value.relational.lower.*;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.asList;

public enum NamedValueConfiguration implements ValueConfiguration {
    COMPATIBLE_MODE {
        @Override
        public NumberExtractor numberExtractor() {
            return NUMBER_EXTRACTOR;
        }

        @Override
        public RelationalComparator equalComparator() {
            return new CompositeRelationalComparator(asList(
                    new BooleanEqualComparator(),
                    new StringEqualComparator(),
                    new NullAndStringEqualComparator(),
                    new NumberStringEqualComparator(),
                    new NumberEqualComparator()
            ));
        }

        @Override
        public RelationalComparator identicalComparator() {
            return new SameTypeComparator(equalComparator());
        }

        @Override
        public RelationalComparator lowerComparator() {
            return new CompositeRelationalComparator(Arrays.asList(
                    new BooleanAndNumberLowerComparator(),
                    new BooleanAndStringLowerComparator(),
                    new NumberAndStringLowerComparator(),
                    new NullAndNumberLowerComparator(),
                    new NullAndStringLowerComparator(),
                    new StringLowerComparator(),
                    new NumberLowerComparator()
            ));
        }

        @Override
        public RelationalComparator greaterComparator() {
            return new CompositeRelationalComparator(Arrays.<RelationalComparator>asList(
                    new NotComparator(lowerComparator()),
                    new NotComparator(equalComparator())
            ));
        }

        @Override
        public TypeExtractor typeExtractor() {
            return TYPE_EXTRACTOR;
        }

        @Override
        public BooleanExtractor booleanExtractor() {
            return BOOLEAN_EXTRACTOR;
        }

        @Override
        public CollectionExtractor collectionExtractor() {
            return COLLECTION_EXTRACTOR;
        }

        @Override
        public MapExtractor mapExtractor() {
            return MAP_EXTRACTOR;
        }

        @Override
        public MapSelectionExtractor mapSelectionExtractor() {
            return MAP_SELECTION_EXTRACTOR;
        }
    },
    EFFICIENT_MODE {
        @Override
        public NumberExtractor numberExtractor() {
            return new CompositeNumberExtractor(asList(
                    new NullNumberExtractor(),
                    new BooleanNumberExtractor(),
                    new NativeNumberExtractor(),
                    new ObjectNumberExtractor()
            ));
        }

        @Override
        public RelationalComparator equalComparator() {
            return new RelationalComparator() {
                @Override
                public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
                    return Optional.of(Objects.equals(left.asObject(), right.asObject()));
                }
            };
        }

        @Override
        public RelationalComparator identicalComparator() {
            return new RelationalComparator() {
                @Override
                public Optional<Boolean> apply(JtwigValue left, JtwigValue right) {
                    return Optional.of(Objects.equals(left.asObject(), right.asObject()));
                }
            };
        }

        @Override
        public RelationalComparator lowerComparator() {
            return new CompositeRelationalComparator(asList(
                    new NumberLowerComparator(),
                    new StringLowerComparator(),
                    new ComparableLowerComparator()
            ));
        }

        @Override
        public RelationalComparator greaterComparator() {
            return new CompositeRelationalComparator(asList(
                    new NumberGreaterComparator(),
                    new StringGreaterComparator(),
                    new ComparableGreaterComparator()
            ));
        }

        @Override
        public TypeExtractor typeExtractor() {
            return TYPE_EXTRACTOR;
        }

        @Override
        public BooleanExtractor booleanExtractor() {
            return BOOLEAN_EXTRACTOR;
        }

        @Override
        public CollectionExtractor collectionExtractor() {
            return COLLECTION_EXTRACTOR;
        }

        @Override
        public MapExtractor mapExtractor() {
            return MAP_EXTRACTOR;
        }

        @Override
        public MapSelectionExtractor mapSelectionExtractor() {
            return MAP_SELECTION_EXTRACTOR;
        }
    }
    ;
    public static final CompositeNumberExtractor NUMBER_EXTRACTOR = new CompositeNumberExtractor(asList(
            new NullNumberExtractor(),
            new BooleanNumberExtractor(),
            new NativeNumberExtractor(),
            new ObjectNumberExtractor()
    ));
    public static final CompositeMapExtractor MAP_EXTRACTOR = new CompositeMapExtractor(asList(
            new NullMapExtractor(),
            new NativeMapExtractor(),
            new IterableMapExtractor(),
            new ArrayMapExtractor()
    ));
    public static final CompositeCollectionExtractor COLLECTION_EXTRACTOR = new CompositeCollectionExtractor(asList(
            new NullCollectionExtractor(),
            new IterableCollectionExtractor(),
            new ArrayCollectionExtractor(),
            new MapCollectionExtractor()
    ));
    public static final CompositeBooleanExtractor BOOLEAN_EXTRACTOR = new CompositeBooleanExtractor(asList(
            new NullBooleanExtractor(),
            new NativeBooleanExtractor(),
            new IterableBooleanExtractor(),
            new ArrayBooleanExtractor(),
            new MapBooleanExtractor(),
            new NumberBooleanExtractor(new CompositeNumberExtractor(asList(
                    new NullNumberExtractor(),
                    new BooleanNumberExtractor(),
                    new NativeNumberExtractor(),
                    new ObjectNumberExtractor()
            ))),
            new StringBooleanExtractor()
    ));
    public static final CompositeTypeExtractor TYPE_EXTRACTOR = new CompositeTypeExtractor(asList(
            new AnyTypeExtractor(),
            new BooleanTypeExtractor(),
            new StringTypeExtractor(),
            new NumberTypeExtractor()
    ));

    public static final CompositeMapSelectionExtractor MAP_SELECTION_EXTRACTOR = new CompositeMapSelectionExtractor(Arrays.<MapSelectionExtractor>asList(
            new NativeMapSelectionExtractor(),
            new ArrayMapSelectionExtractor()
    ));
}
