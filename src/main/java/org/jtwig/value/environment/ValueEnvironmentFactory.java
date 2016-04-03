package org.jtwig.value.environment;

import org.jtwig.value.config.ValueConfiguration;
import org.jtwig.value.convert.CompositeConverter;

public class ValueEnvironmentFactory {
    public ValueEnvironment create (ValueConfiguration configuration) {
        return new ValueEnvironment(
                configuration.getMathContext(),
                configuration.getRoundingMode(),
                new CompositeConverter<>(configuration.getNumberConverters()),
                new CompositeConverter<>(configuration.getBooleanConverters()),
                new CompositeConverter<>(configuration.getCollectionConverters()),
                new CompositeConverter<>(configuration.getCharConverters()),
                configuration.getValueComparator(), configuration.getStringConverter());
    }
}
