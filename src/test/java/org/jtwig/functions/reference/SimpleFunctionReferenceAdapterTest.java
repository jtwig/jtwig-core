package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.SimpleFunction;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleFunctionReferenceAdapterTest {
    private SimpleFunction function = mock(SimpleFunction.class);
    private SimpleFunctionReferenceAdapter underTest = new SimpleFunctionReferenceAdapter(function);

    @Test
    public void calculate() throws Exception {
        List<FunctionArgument> functionArguments = asList(new FunctionArgument(Optional.<String>absent(), "test"));
        when(function.execute("test")).thenReturn("hello");

        Optional<Supplier> result = underTest.calculate(functionArguments);

        assertEquals(result.get().get(), "hello");
    }

    @Test
    public void name() throws Exception {
        when(function.name()).thenReturn("one");

        String result = underTest.name();

        assertEquals(result, "one");
    }
}