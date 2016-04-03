package org.jtwig.value.convert.string;

public class DefaultStringConverter implements StringConverter {
    @Override
    public String convert(Object input) {
        return input == null ? "" : input.toString();
    }
}
