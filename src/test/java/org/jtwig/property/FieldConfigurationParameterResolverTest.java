package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FieldConfigurationParameterResolverTest {
    private final Position position = mock(Position.class);
    private final List<FunctionArgument> arguments = new ArrayList<>();
    private FieldPropertyResolver underTest;

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void resolvePropertyWhenAccessPrivate() throws Exception {
        underTest = new FieldPropertyResolver(true);
        PropertyResolveRequest request = new PropertyResolveRequest(position, new TestExample("example"), "value", arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("example", result.get().asObject());
    }

    @Test
    public void resolvePropertyWhenAccessPrivateNotExists() throws Exception {
        underTest = new FieldPropertyResolver(true);
        PropertyResolveRequest request = new PropertyResolveRequest(position, new TestExample("example"), "value1", arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolvePropertyWhenNoAccessPrivate() throws Exception {
        underTest = new FieldPropertyResolver(false);
        PropertyResolveRequest request = new PropertyResolveRequest(position, new TestExample("example"), "value", arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    private static class TestExample {
        private final String value;

        public TestExample(String value) {
            this.value = value;
        }
    }
}