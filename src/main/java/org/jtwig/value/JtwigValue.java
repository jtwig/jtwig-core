package org.jtwig.value;

import com.google.common.base.Optional;
import org.jtwig.value.compare.JtwigValueLooseComparator;
import org.jtwig.util.OptionalUtils;
import org.jtwig.value.extract.bool.BooleanExtractor;
import org.jtwig.value.extract.collection.CollectionExtractor;
import org.jtwig.value.extract.map.MapExtractor;
import org.jtwig.value.extract.number.NumberExtractor;
import org.jtwig.value.extract.type.TypeExtractor;

import java.math.BigDecimal;
import java.util.*;

public class JtwigValue {
    private final Object value;
    private final TypeExtractor typeExtractor;
    private final NumberExtractor numberExtractor;
    private final CollectionExtractor collectionExtractor;
    private final MapExtractor mapExtractor;
    private final BooleanExtractor booleanExtractor;

    public JtwigValue(Object value, TypeExtractor typeExtractor, NumberExtractor numberExtractor, CollectionExtractor collectionExtractor, MapExtractor mapExtractor, BooleanExtractor booleanExtractor) {
        this.value = value;
        this.typeExtractor = typeExtractor;
        this.numberExtractor = numberExtractor;
        this.collectionExtractor = collectionExtractor;
        this.mapExtractor = mapExtractor;
        this.booleanExtractor = booleanExtractor;
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
        return numberExtractor.extract(value);
    }
    public Boolean asBoolean () {
        return booleanExtractor.extract(value)
                .or(true);
    }
    public Collection<Object> asCollection() {
        return collectionExtractor.extract(value)
                .or(Collections.singletonList(value));
    }
    public Map<Object, Object> asMap() {
        return mapExtractor.extract(value)
                .or(new HashMap(){{ put(0, value); }});
    }
    public Character asChar() {
        return asString().charAt(0);
    }
    public BigDecimal mandatoryNumber () {
        return asNumber().or(OptionalUtils.<BigDecimal, IllegalArgumentException>throwException(new IllegalArgumentException(String.format("Unable to convert '%s' into a number", value))));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof JtwigValue) {
            return Objects.compare(this, (JtwigValue) other, JtwigValueLooseComparator.instance()) == 0;
        } else {
            return super.equals(other);
        }
    }

    public JtwigType getType () {
        return typeExtractor
                .extract(value)
                .or(JtwigType.OBJECT);
    }


    @Override
    public String toString() {
        return String.format("<%s>", value);
    }
}
