package org.jtwig.property;

import com.google.common.base.Optional;

import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.asList;
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
        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("oi", result.get().asObject());
    }

    @Test
    public void resolveMethodWhenNameMatchesWithArguments() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());
        arguments.add(new FunctionArgument(Optional.<String>absent(), new JtwigValue("test")));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("test", result.get().asObject());
    }

    @Test
    public void resolveMethodWhenNameMatchesWithArgumentsNull() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());
        arguments.add(new FunctionArgument(Optional.<String>absent(), new JtwigValue(null)));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);
        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals(null, result.get().asObject());
    }

    @Test
    public void resolveMethodWhenNameMatchesWithArgumentsNotMatchingType() throws Exception {
        underTest = new MethodPropertyResolver(exactlyEqual());
        arguments.add(new FunctionArgument(Optional.<String>absent(), new JtwigValue(1)));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "hello", arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveMethodWithPrefixed() throws Exception {
        underTest = new MethodPropertyResolver(prefixedEqual("get"));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new SimpleTest(), "test", arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("test", result.get().asObject());
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