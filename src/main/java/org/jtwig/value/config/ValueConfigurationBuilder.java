package org.jtwig.value.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.compare.ValueComparator;
import org.jtwig.value.convert.Converter;
import org.jtwig.value.convert.string.StringConverter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

public class ValueConfigurationBuilder<B extends ValueConfigurationBuilder> implements Builder<ValueConfiguration> {
    private MathContext mathContext;
    private RoundingMode roundingMode;
    private final Collection<Converter<Boolean>> booleanConverters = new ArrayList<>();
    private final Collection<Converter<BigDecimal>> numberConverters = new ArrayList<>();
    private final Collection<Converter<WrappedCollection>> collectionConverters = new ArrayList<>();
    private final Collection<Converter<Character>> charConverters = new ArrayList<>();
    private ValueComparator valueComparator;
    private StringConverter stringConverter;

    public ValueConfigurationBuilder() {}

    public ValueConfigurationBuilder(ValueConfiguration prototype) {
        this.mathContext = prototype.getMathContext();
        this.roundingMode = prototype.getRoundingMode();
        this.booleanConverters.addAll(prototype.getBooleanConverters());
        this.numberConverters.addAll(prototype.getNumberConverters());
        this.collectionConverters.addAll(prototype.getCollectionConverters());
        this.charConverters.addAll(prototype.getCharConverters());
        this.valueComparator = prototype.getValueComparator();
        this.stringConverter = prototype.getStringConverter();
    }

    public B withMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return self();
    }

    public B withRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return self();
    }

    public B withBooleanConverters(Collection<Converter<Boolean>> booleanConverters) {
        this.booleanConverters.addAll(booleanConverters);
        return self();
    }

    public B withNumberConverters(Collection<Converter<BigDecimal>> numberConverters) {
        this.numberConverters.addAll(numberConverters);
        return self();
    }

    public B withCollectionConverters(Collection<Converter<WrappedCollection>> collectionConverters) {
        this.collectionConverters.addAll(collectionConverters);
        return self();
    }

    public B withBooleanConverter(Converter<Boolean> booleanConverter) {
        this.booleanConverters.add(booleanConverter);
        return self();
    }

    public B withNumberConverter(Converter<BigDecimal> numberConverter) {
        this.numberConverters.add(numberConverter);
        return self();
    }

    public B withCollectionConverter(Converter<WrappedCollection> collectionConverter) {
        this.collectionConverters.add(collectionConverter);
        return self();
    }

    public B withCharConverter(Converter<Character> charConverter) {
        this.charConverters.add(charConverter);
        return self();
    }

    public B withCharConverters(Collection<Converter<Character>> charConverters) {
        this.charConverters.addAll(charConverters);
        return self();
    }

    public B withValueComparator(ValueComparator valueComparator) {
        this.valueComparator = valueComparator;
        return self();
    }

    public B withStringConverter(StringConverter stringConverter) {
        this.stringConverter = stringConverter;
        return self();
    }

    private B self() {
        return (B) this;
    }

    @Override
    public ValueConfiguration build() {
        return new ValueConfiguration(mathContext, roundingMode, booleanConverters, numberConverters, collectionConverters, charConverters, valueComparator, stringConverter);
    }
}
