package org.jtwig.configuration;

public enum BooleanConfigurationParameters implements ConfigurationParameter<Boolean> {
    STRICT_MODE(false)
    ;

    private final Boolean defaultValue;

    BooleanConfigurationParameters(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Boolean defaultValue() {
        return defaultValue;
    }
}
