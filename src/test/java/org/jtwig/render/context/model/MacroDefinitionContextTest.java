package org.jtwig.render.context.model;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MacroDefinitionContextTest {
    private final HashMap<String, Macro> map = new HashMap<>();
    private MacroDefinitionContext underTest = new MacroDefinitionContext(map);

    @Before
    public void setUp() throws Exception {
        map.clear();
    }

    @Test
    public void resolveNotPresent() throws Exception {
        String name = "name";

        Optional<Macro> result = underTest.resolve(name);

        assertFalse(result.isPresent());
    }
    @Test
    public void resolvePresent() throws Exception {
        String name = "name";
        Macro macro = mock(Macro.class);

        map.put(name, macro);

        Optional<Macro> result = underTest.resolve(name);

        assertTrue(result.isPresent());
        assertEquals(macro, result.get());
    }

    @Test
    public void add() throws Exception {
        Macro macro = mock(Macro.class);
        String identifier = "test";

        underTest.add(identifier, macro);

        assertSame(map.get(identifier), macro);
    }
}