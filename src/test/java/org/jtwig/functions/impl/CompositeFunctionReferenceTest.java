package org.jtwig.functions.impl;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.functions.reference.CompositeFunctionReference;
import org.jtwig.functions.reference.FunctionReference;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeFunctionReferenceTest {
    private final ArrayList<FunctionReference> list = new ArrayList<>();
    private final String name = "name";
    private CompositeFunctionReference underTest = new CompositeFunctionReference(name, list);

    @Before
    public void setUp() throws Exception {
        list.clear();
    }

    @Test
    public void name() throws Exception {
        assertThat(underTest.name(), is(name));
    }

    @Test
    public void executeWhenNoFunction() throws Exception {
        Optional<Supplier> result = underTest.calculate(new ArrayList<FunctionArgument>());

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void executeWhenFunctionNotFound() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();
        FunctionReference function = mock(FunctionReference.class);
        when(function.calculate(arguments)).thenReturn(Optional.<Supplier>absent());
        list.add(function);

        Optional<Supplier> result = underTest.calculate(arguments);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void executeWhenFunctionFound() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();
        FunctionReference function = mock(FunctionReference.class);
        Optional<Supplier> optional = Optional.of(mock(Supplier.class));
        when(function.calculate(arguments)).thenReturn(optional);
        list.add(function);

        Optional<Supplier> result = underTest.calculate(arguments);

        assertThat(result, is(optional));
    }
}