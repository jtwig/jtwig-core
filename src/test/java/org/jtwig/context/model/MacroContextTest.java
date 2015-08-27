package org.jtwig.context.model;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MacroContextTest {
    private Map<String, Macro> macros = new HashMap<>();
    private MacroContext underTest = new MacroContext(macros);

    @Before
    public void setUp() throws Exception {
        macros.clear();
    }

    @Test
    public void resolveWhenEmpty() throws Exception {
        Optional<Macro> result = underTest.resolve("some");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenFound() throws Exception {
        Macro macro = mock(Macro.class);
        macros.put("some", macro);

        Optional<Macro> result = underTest.resolve("some");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(macro));
    }
}