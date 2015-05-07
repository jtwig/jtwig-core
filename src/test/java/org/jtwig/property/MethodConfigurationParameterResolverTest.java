package org.jtwig.property;

import com.google.common.base.Optional;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.jtwig.property.MethodPropertyResolver.exactlyEqual;
import static org.jtwig.property.MethodPropertyResolver.prefixedEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MethodConfigurationParameterResolverTest {
    private final Position position = mock(Position.class);
    private final ArrayList<FunctionArgument> arguments = new ArrayList<>();
    private MethodPropertyResolver underTest;

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void resolveMethodWhenNameMatchesWithoutArguments() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());

        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);
        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("oi", result.get().getValue());
    }

    @Test
    public void resolveMethodWhenNameMatchesWithArguments() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());
        arguments.add(new FunctionArgument(Optional.<String>absent(), "test"));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("test", result.get().getValue());
    }

    @Test
    public void resolveMethodWhenNameMatchesWithArgumentsNull() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());
        arguments.add(new FunctionArgument(Optional.<String>absent(), null));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);
        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals(null, result.get().getValue());
    }

    @Test
    public void resolveMethodWhenNameMatchesWithArgumentsNotMatchingType() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());
        arguments.add(new FunctionArgument(Optional.<String>absent(), 1));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveMethodWithPrefixed() throws Exception {
        underTest = new MethodPropertyResolver(prefixedEqual("get"));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "test", arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("test", result.get().getValue());
    }

    private static class SimpleTest {
        public String hello () {
            return "oi";
        }

        public String getTest () {
            return "test";
        }

        public String hello (String value) {
            return value;
        }
    }
}