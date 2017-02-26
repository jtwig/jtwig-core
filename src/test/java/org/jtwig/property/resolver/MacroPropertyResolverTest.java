package org.jtwig.property.resolver;

import com.google.common.base.Optional;
import org.jtwig.macro.ImportedMacros;
import org.jtwig.macro.Macro;
import org.jtwig.macro.render.MacroRender;
import org.jtwig.property.resolver.request.PropertyResolveRequest;
import org.jtwig.reflection.model.Value;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MacroPropertyResolverTest {
    private final MacroRender macroRender = mock(MacroRender.class);
    private final ImportedMacros importedMacros = mock(ImportedMacros.class);
    private MacroPropertyResolver underTest = new MacroPropertyResolver(macroRender, importedMacros);

    @Test
    public void property() throws Exception {
        String reference = "test";
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);

        given(request.getPropertyName()).willReturn(Optional.of(reference));
        given(importedMacros.resolve(reference)).willReturn(Optional.<Macro>absent());

        Optional<Value> result = underTest.resolve(request);

        assertEquals(Optional.<Value>absent(), result);
    }
}