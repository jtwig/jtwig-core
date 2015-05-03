package org.jtwig.property;

import org.jtwig.context.RenderContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

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