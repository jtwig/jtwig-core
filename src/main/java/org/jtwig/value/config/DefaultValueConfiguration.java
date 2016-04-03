package org.jtwig.value.config;

import org.jtwig.value.WrappedCollection;
import org.jtwig.value.compare.DefaultValueComparator;
import org.jtwig.value.convert.Converter;
import org.jtwig.value.convert.NullConverter;
import org.jtwig.value.convert.bool.BooleanConverter;
import org.jtwig.value.convert.character.CharConverter;
import org.jtwig.value.convert.collection.ArrayToCollectionConverter;
import org.jtwig.value.convert.collection.IterableToCollectionConverter;
import org.jtwig.value.convert.collection.MapToCollectionConverter;
import org.jtwig.value.convert.number.BigDecimalConverter;
import org.jtwig.value.convert.string.DefaultStringConverter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class DefaultValueConfiguration extends ValueConfiguration {
    public DefaultValueConfiguration() {
        super(MathContext.DECIMAL32, RoundingMode.HALF_UP, Arrays.<Converter<Boolean>>asList(
                new BooleanConverter()
        ), Arrays.<Converter<BigDecimal>>asList(
                new BigDecimalConverter()
        ), Arrays.asList(
                new NullConverter<WrappedCollection>(),
                new ArrayToCollectionConverter(),
                new IterableToCollectionConverter(),
                new MapToCollectionConverter()
        ), Arrays.asList(
                new NullConverter<Character>(),
                new CharConverter()
        ), new DefaultValueComparator(),
                new DefaultStringConverter());
    }
}
