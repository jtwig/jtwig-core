package org.jtwig.value;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.configuration.ValueConfiguration;
import org.jtwig.value.extract.number.ObjectNumberExtractor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JtwigValue {
    private final Object value;
    private final ValueConfiguration configuration;

    public JtwigValue(Object value, ValueConfiguration configuration) {
        this.value = value;
        this.configuration = configuration;
    }

    public boolean isPresent () {
        return value != null;
    }
    public boolean isNull () {
        return value == null;
    }
    public boolean isUndefined() {
        return value == Undefined.UNDEFINED;
    }

    public String asString() {
        if (value == null) return "";
        else return value.toString();
    }
    public Object asObject() {
        return value;
    }
    public Optional<BigDecimal> asNumber() {
        return configuration.numberExtractor().extract(value);
    }
    public Boolean asBoolean () {
        return configuration.booleanExtractor()
                .extract(value)
                .or(false);
    }
    public Collection<Object> asCollection() {
        return configuration.collectionExtractor().extract(value)
                .or(Collections.singletonList(value));
    }
    public Map<Object, Object> asMap() {
        return configuration.mapExtractor().extract(value)
                .or(new HashMap(){{ put(0, value); }});
    }
    public Character asChar() {
        return asString().charAt(0);
    }
    public BigDecimal mandatoryNumber () {
        return asNumber().or(OptionalUtils.<BigDecimal, IllegalArgumentException>throwException(new IllegalArgumentException(String.format("Unable to convert '%s' into a number", value))));
    }

    public boolean isEqualTo(JtwigValue other) {
        return configuration.equalComparator()
                .apply(this, other)
                .or(false);
    }

    public boolean isIdenticalTo (JtwigValue other) {
        return configuration.identicalComparator()
                .apply(this, other)
                .or(false);
    }

    public boolean isLowerThan (JtwigValue other) {
        return configuration.lowerComparator()
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
        return configuration.typeExtractor()
                .extract(value)
                .or(JtwigType.OBJECT);
    }

    @Override
    public String toString() {
        return String.format("<%s>", value);
    }
}
