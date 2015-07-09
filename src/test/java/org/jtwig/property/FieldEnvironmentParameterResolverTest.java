package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.converter.Converter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FieldEnvironmentParameterResolverTest {
    private final Position position = mock(Position.class);
    private final List<FunctionArgument> arguments = new ArrayList<>();
    private FieldPropertyResolver underTest;
    private Converter converter = mock(Converter.class);

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void resolvePropertyWhenAccessPrivate() throws Exception {
        underTest = new FieldPropertyResolver(true);
        PropertyResolveRequest request = new PropertyResolveRequest(position, new Value(new TestExample("example")), "value", arguments, converter);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals("example", result.get().getValue());
    }

    @Test
    public void resolvePropertyWhenAccessPrivateNotExists() throws Exception {
        underTest = new FieldPropertyResolver(true);
        PropertyResolveRequest request = new PropertyResolveRequest(position, new Value(new TestExample("example")), "value1", arguments, converter);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolvePropertyWhenNoAccessPrivate() throws Exception {
        underTest = new FieldPropertyResolver(false);
        PropertyResolveRequest request = new PropertyResolveRequest(position, new Value(new TestExample("example")), "value", arguments, converter);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    private static class TestExample {
        private final String value;

        public TestExample(String value) {
            this.value = value;
        }
    }
}