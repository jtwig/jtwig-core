package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.property.macro.MacroRender;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.context.model.Macro;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MacroPropertyResolverTest {
    private final MacroRender macroRender = mock(MacroRender.class);
    private final MacroDefinitionContext macroDefinitionContext = mock(MacroDefinitionContext.class);
    private MacroPropertyResolver underTest = new MacroPropertyResolver(macroRender, macroDefinitionContext);

    @Test
    public void property() throws Exception {
        String reference = "test";
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getPropertyName()).willReturn(Optional.of(reference));
        given(macroDefinitionContext.resolve(reference)).willReturn(Optional.<Macro>absent());

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.<Value>absent(), result);
    }
}