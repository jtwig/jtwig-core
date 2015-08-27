package org.jtwig.functions.impl;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.reference.FunctionReference;
import org.jtwig.functions.resolver.CoreFunctionResolver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoreFunctionResolverTest {
    private final HashMap<String, FunctionReference> functions = new HashMap<>();
    private final ArrayList<FunctionArgument> arguments = new ArrayList<>();
    private CoreFunctionResolver underTest = new CoreFunctionResolver(functions);

    @Before
    public void setUp() throws Exception {
        functions.clear();
        arguments.clear();
    }

    @Test
    public void resolveWhenNotFound() throws Exception {
        Optional<Supplier> result = underTest.resolve("test", arguments);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenNotReturned() throws Exception {
        FunctionReference function = mock(FunctionReference.class);
        when(function.calculate(arguments)).thenReturn(Optional.<Supplier>absent());
        functions.put("test", function);

        Optional<Supplier> result = underTest.resolve("test", arguments);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenAndReturned() throws Exception {
        FunctionReference function = mock(FunctionReference.class);
        Supplier executable = mock(Supplier.class);
        when(function.calculate(arguments)).thenReturn(Optional.of(executable));
        functions.put("test", function);

        Optional<Supplier> result = underTest.resolve("test", arguments);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(executable));
    }
}