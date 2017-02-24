package org.jtwig.render.context.model;

import org.jtwig.escape.EscapeEngine;
import org.jtwig.render.context.ContextItem;
import org.jtwig.render.context.StackedContext;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.context.ValueContext;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class RenderContextTest {

    @Test
    public void getPropertiesContext() throws Exception {
        StackedContext<PropertiesContext> propertiesContext = mock(StackedContext.class);

        RenderContext underTest = new RenderContext(StackedContext.<ValueContext>emptyContext(),
                StackedContext.<EscapeEngine>emptyContext(),
                StackedContext.<ContextItem<ResourceReference>>emptyContext(),
                StackedContext.<BlockContext>emptyContext(),
                StackedContext.<MacroDefinitionContext>emptyContext(),
                StackedContext.<MacroAliasesContext>emptyContext(),
                propertiesContext);

        assertSame(propertiesContext, underTest.getPropertiesContext());
    }
}