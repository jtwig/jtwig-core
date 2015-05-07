package org.jtwig.configuration;

import org.jtwig.content.json.JsonMapperFactory;
import org.jtwig.content.spaces.SpaceRemover;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.expression.lists.EnumerationListStrategy;
import org.jtwig.parser.JtwigParser;
import org.jtwig.property.PropertyResolver;
import org.jtwig.resource.resolver.ResourceResolver;
import org.jtwig.value.configuration.ValueConfiguration;

import java.math.MathContext;

public interface Configuration {
    JtwigParser parser();
    ResourceResolver resourceResolver();
    FunctionResolver functionResolver();
    PropertyResolver propertyResolver();
    JsonMapperFactory jsonMapper();
    EnumerationListStrategy enumerationStrategy();
    SpaceRemover spaceRemover();
    boolean strictMode ();
    MathContext mathContext ();
    EscapeMode initialEscapeMode ();
    ValueConfiguration valueConfiguration ();

    <T> T parameter(String name, T defaultValue);
}
