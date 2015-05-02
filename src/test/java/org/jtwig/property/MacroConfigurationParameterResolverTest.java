package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.Macro;
import org.jtwig.context.model.MacroContext;
import org.jtwig.model.tree.Node;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.ByteArrayRenderable;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MacroConfigurationParameterResolverTest {
    private final MacroPropertyResolver underTest = new MacroPropertyResolver();
    private final RenderContext renderContext = mock(RenderContext.class);

//    @Test
//    public void resolveOk() throws Exception {
//        HashMap<String, Macro> macros = new HashMap<>();
//        Node content = mock(Node.class);
//
//        macros.put("test", new Macro(asList("arg1"), content));
//        MacroContext macroContext = new MacroContext(macros) {
//            @Override
//            protected RenderContext getRenderContext() {
//                return renderContext;
//            }
//        };
//
//        Renderable renderable = new ByteArrayRenderable("joao".getBytes());
//        when(renderContext.render(content)).thenReturn(renderable);
//
//
//        Optional<JtwigValue> optional = underTest.resolve(macroContext, "test", new ArrayList<>());
//
//        assertThat(optional.isPresent(), is(true));
//        assertEquals("joao", optional.get().asObject());
//    }
//
//    @Test
//    public void resolveWhenNoMacro() throws Exception {
//        HashMap<String, Macro> macros = new HashMap<>();
//        Node content = mock(Node.class);
//
//        MacroContext macroContext = new MacroContext(macros) {
//            @Override
//            protected RenderContext getRenderContext() {
//                return renderContext;
//            }
//        };
//
//        Optional<JtwigValue> optional = underTest.resolve(macroContext, "test", new ArrayList<>());
//
//        assertThat(optional.isPresent(), is(false));
//    }
}