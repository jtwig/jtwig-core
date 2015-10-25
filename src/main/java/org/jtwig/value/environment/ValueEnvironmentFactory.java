package org.jtwig.value.environment;

import org.jtwig.value.configuration.ValueConfiguration;
import org.jtwig.value.converter.CompositeConverter;

public class ValueEnvironmentFactory {
    public ValueEnvironment crete(ValueConfiguration configuration) {
        return new ValueEnvironment(
                configuration.getMathContext(),
                configuration.getEqualComparator(),
                configuration.getIdenticalComparator(),
                configuration.getLowerComparator(),
                configuration.getGreaterComparator(),
                configuration.getTypeExtractor(),
                configuration.getMapSelectionExtractor(),
                new CompositeConverter(configuration.getConverter()));
    }
}
