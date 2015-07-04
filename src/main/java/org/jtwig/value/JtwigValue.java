package org.jtwig.value;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.configuration.ValueConfiguration;
import org.jtwig.value.extract.number.ObjectNumberExtractor;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

public class JtwigValue {
    private final Object value;
    private final Converter converter;
    private final ValueConfiguration configuration;

    public JtwigValue(Object value, Converter converter, ValueConfiguration configuration) {
        this.value = value;
        this.converter = converter;
        this.configuration = configuration;
    }

    public boolean isPresent () {
        return value != null;
    }
    public boolean isNull () {
        return value == null;
    }
    public boolean isDefined() {
        return value == Undefined.UNDEFINED;
    }

    public boolean isUndefined() {
        return value == Undefined.UNDEFINED;
    }
    public String asString() {
        return as(String.class)
                .or("");
    }
    public String asString(Charset charset) {
        return new String(as(String.class)
                .or("").getBytes(), charset);
    }
    public Object asObject() {
        return value;
    }
    public Optional<BigDecimal> asNumber() {
        return as(BigDecimal.class);
    }
    public Boolean asBoolean () {
        return as(Boolean.class)
                .or(false);
    }
    public Collection<Object> asCollection() {
        return as(Collection.class)
                .or(Collections.singletonList(value));
    }
    public Map<Object, Object> asMap() {
        return as(Map.class)
                .or(new HashMap() {{
                    put(0, value);
                }});
    }
    public Character asChar() {
        return asString().charAt(0);
    }

    public BigDecimal mandatoryNumber () {
        return asNumber().or(OptionalUtils.<BigDecimal, IllegalArgumentException>throwException(new IllegalArgumentException(String.format("Unable to convert '%s' into a number", value))));
    }
    public boolean isEqualTo(JtwigValue other) {
        return configuration.getEqualComparator()
                .apply(this, other)
                .or(false);
    }
    public boolean isIdenticalTo (JtwigValue other) {
        return configuration.getIdenticalComparator()
                .apply(this, other)
                .or(false);
    }
    public boolean isLowerThan (JtwigValue other) {
        return configuration.getLowerComparator()
                .apply(this, other)
                .or(false);
    }
    public boolean isGreaterThan(JtwigValue value) {
        return !isLowerThan(value) && !isEqualTo(value);
    }

    public boolean isStringNumber() {
        if (getType() == JtwigType.STRING) {
            return value.toString().matches(ObjectNumberExtractor.NUMBER_PATTERN);
        }
        return false;
    }

    public JtwigType getType () {
        return configuration.getTypeExtractor()
                .extract(value)
                .or(JtwigType.OBJECT);
    }

    public JtwigValue map(Function<JtwigValue, Object> map) {
        return new JtwigValue(map.apply(this), converter, configuration);
    }

    public boolean contains(JtwigValue value) {
        for (Object item : asCollection()) {
            if (value.isEqualTo(new JtwigValue(item, converter, configuration))) {
                return true;
            }
        }
        return false;
    }

    public JtwigValue getMapValue(JtwigValue key) {
        Object value = configuration.getMapSelectionExtractor().extract(asMap(), key)
                .or(new Value(Undefined.UNDEFINED)).getValue();
        return new JtwigValue(value, converter, configuration);
    }

    public Converter converter () {
        return converter;
    }

    public <T> Optional<T> as(Class<T> type) {
        return converter
                .convert(value, type)
                .transform(new Function<Value, T>() {
                    @Override
                    public T apply(Value input) {
                        return (T) input.getValue();
                    }
                });
    }
}
