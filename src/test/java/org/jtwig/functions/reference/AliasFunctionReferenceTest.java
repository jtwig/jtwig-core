package org.jtwig.functions.reference;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.functions.FunctionArgument;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AliasFunctionReferenceTest {
    private final FunctionReference functionReference = mock(FunctionReference.class);
    private AliasFunctionReference underTest = new AliasFunctionReference("name", functionReference);

    @Test
    public void calculate() throws Exception {
        ArrayList<FunctionArgument> arguments = new ArrayList<>();
        Optional value = mock(Optional.class);
        when(functionReference.calculate(arguments)).thenReturn(value);

        Optional<Supplier> result = underTest.calculate(arguments);

        assertSame(result, value);
    }
    @Test
    public void name() throws Exception {
        String result = underTest.name();

        assertSame(result, "name");
    }
}