package org.jtwig.value.configuration;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.value.JtwigType;
import org.jtwig.value.converter.Converter;
import org.jtwig.value.extract.Extractor;
import org.jtwig.value.extract.map.selection.MapSelectionExtractor;
import org.jtwig.value.relational.RelationalComparator;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;

public class ValueConfigurationBuilder<B extends ValueConfigurationBuilder> implements Builder<ValueConfiguration> {
    private MathContext mathContext;
    private RelationalComparator equalComparator;
    private RelationalComparator identicalComparator;
    private RelationalComparator lowerComparator;
    private RelationalComparator greaterComparator;
    private Extractor<JtwigType> typeExtractor;
    private MapSelectionExtractor mapSelectionExtractor;
    private final Collection<Converter> converter = new ArrayList<>();


    public ValueConfigurationBuilder () {}
    public ValueConfigurationBuilder (ValueConfiguration prototype) {
        this
            .withMathContext(prototype.getMathContext())
            .withConverters(prototype.getConverter())
            .withEqualComparator(prototype.getEqualComparator())
            .withIdenticalComparator(prototype.getIdenticalComparator())
            .withGreaterComparator(prototype.getGreaterComparator())
            .withLowerComparator(prototype.getLowerComparator())
            .withMapSelectionExtractor(prototype.getMapSelectionExtractor())
            .withTypeExtractor(prototype.getTypeExtractor())
        ;
    }

    public B withMathContext(MathContext mathContext) {
        this.mathContext = mathContext;
        return self();
    }

    public B withEqualComparator(RelationalComparator equalComparator) {
        this.equalComparator = equalComparator;
        return self();
    }

    public B withIdenticalComparator(RelationalComparator identicalComparator) {
        this.identicalComparator = identicalComparator;
        return self();
    }

    public B withLowerComparator(RelationalComparator lowerComparator) {
        this.lowerComparator = lowerComparator;
        return self();
    }

    public B withGreaterComparator(RelationalComparator greaterComparator) {
        this.greaterComparator = greaterComparator;
        return self();
    }

    public B withTypeExtractor(Extractor<JtwigType> typeExtractor) {
        this.typeExtractor = typeExtractor;
        return self();
    }

    public B withMapSelectionExtractor(MapSelectionExtractor mapSelectionExtractor) {
        this.mapSelectionExtractor = mapSelectionExtractor;
        return self();
    }

    public B withConverters(Collection<Converter> converters) {
        this.converter.addAll(converters);
        return self();
    }

    public B withConverter(Converter converter) {
        this.converter.add(converter);
        return self();
    }

    @Override
    public ValueConfiguration build() {
        return new ValueConfiguration(mathContext, equalComparator, identicalComparator, lowerComparator, greaterComparator, typeExtractor, mapSelectionExtractor, converter);
    }

    protected B self () {
        return (B) this;
    }
}
