package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.reflection.Executable;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.jtwig.functions.FunctionArgument;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeanFunctionReferenceTest {
    private final Supplier<MethodInvoker<FunctionArgument>> methodInvokerSupplier = mock(Supplier.class);
    private final BeanMethod beanMethod = mock(BeanMethod.class);
    private BeanFunctionReference underTest = new BeanFunctionReference("name", beanMethod, methodInvokerSupplier);

    @Test
    public void calculate() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();
        Executable reference = mock(Executable.class);
        MethodInvoker methodInvoker = mock(MethodInvoker.class);
        when(methodInvokerSupplier.get()).thenReturn(methodInvoker);
        when(methodInvoker.invoke(any(MethodInvoker.Request.class))).thenReturn(Optional.of(reference));

        Optional<Supplier> calculate = underTest.calculate(arguments);

        assertThat(calculate.isPresent(), is(true));
        assertSame(calculate.get(), reference);
    }
}