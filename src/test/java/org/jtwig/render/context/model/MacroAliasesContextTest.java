package org.jtwig.render.context.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class MacroAliasesContextTest {
    private final HashMap<String, MacroDefinitionContext> macros = new HashMap<>();
    private MacroAliasesContext underTest = new MacroAliasesContext(macros);

    @Before
    public void setUp() throws Exception {
        macros.clear();
    }

    @Test
    public void with() throws Exception {
        String identifier = "identifier";
        MacroDefinitionContext context = mock(MacroDefinitionContext.class);

        underTest.with(identifier, context);

        assertSame(macros.get(identifier), context);
    }

    @Test
    public void iterator() throws Exception {
        macros.put("a", mock(MacroDefinitionContext.class));
        macros.put("b", mock(MacroDefinitionContext.class));

        Iterator<Map.Entry<String, MacroDefinitionContext>> iterator = underTest.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MacroDefinitionContext> next = iterator.next();

            assertSame(macros.get(next.getKey()), next.getValue());
        }
    }
}