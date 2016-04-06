package org.jtwig.value.config;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.util.builder.ListBuilder;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.compare.ValueComparator;
import org.jtwig.value.convert.Converter;
import org.jtwig.value.convert.string.StringConverter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ValueConfigurationBuilder<B extends ValueConfigurationBuilder> implements Builder<ValueConfiguration> {
    private MathContext mathContext;
    private RoundingMode roundingMode;
    private final ListBuilder<B, Converter<Boolean>> booleanConverters;
    private final ListBuilder<B, Converter<BigDecimal>> numberConverters;
    private final ListBuilder<B, Converter<WrappedCollection>> collectionConverters;
    private final ListBuilder<B, Converter<Character>> charConverters;
    private ValueComparator valueComparator;
    private StringConverter stringConverter;

    public ValueConfigurationBuilder() {
        this.booleanConverters = new ListBuilder<>(self());
        this.numberConverters = new ListBuilder<>(self());
        this.collectionConverters = new ListBuilder<>(self());
        this.charConverters = new ListBuilder<>(self());
    }

    public ValueConfigurationBuilder(ValueConfiguration prototype) {
        this.mathContext = prototype.getMathContext();
        this.roundingMode = prototype.getRoundingMode();
        this.valueComparator = prototype.getValueComparator();
        this.stringConverter = prototype.getStringConverter();
        this.booleanConverters = new ListBuilder<>(self(), prototype.getBooleanConverters());
        this.numberConverters = new ListBuilder<>(self(), prototype.getNumberConverters());
        this.collectionConverters = new ListBuilder<>(self(), prototype.getCollectionConverters());
        this.charConverters = new ListBuilder<>(self(), prototype.getCharConverters());
    }

    public B withMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return self();
    }

    public B withRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return self();
    }

    public ListBuilder<B, Converter<Boolean>> booleanConverters() {
        return booleanConverters;
    }

    public ListBuilder<B, Converter<BigDecimal>> numberConverters() {
        return numberConverters;
    }

    public ListBuilder<B, Converter<WrappedCollection>> collectionConverters() {
        return collectionConverters;
    }

    public ListBuilder<B, Converter<Character>> charConverters() {
        return charConverters;
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
        return new ValueConfiguration(mathContext, roundingMode, booleanConverters.build(),
                numberConverters.build(), collectionConverters.build(),
                charConverters.build(), valueComparator, stringConverter);
    }
}
