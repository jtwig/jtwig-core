package org.jtwig.configuration;

public interface ConfigurationParameter<T> {
    T defaultValue();
    String name();
}
