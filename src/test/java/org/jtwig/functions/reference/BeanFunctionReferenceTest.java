package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.reflection.Executable;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.model.bean.BeanMethod;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeanFunctionReferenceTest {
    private final MethodInvoker<FunctionArgument> methodInvoker = mock(MethodInvoker.class);
    private final BeanMethod beanMethod = mock(BeanMethod.class);
    private BeanFunctionReference underTest = new BeanFunctionReference("name", beanMethod, methodInvoker);

    @Test
    public void calculate() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();
        Executable reference = mock(Executable.class);
        when(methodInvoker.invoke(any(MethodInvoker.Request.class))).thenReturn(Optional.of(reference));

        Optional<Supplier> calculate = underTest.calculate(arguments);

        assertThat(calculate.isPresent(), is(true));
        assertSame(calculate.get(), reference);
    }
}