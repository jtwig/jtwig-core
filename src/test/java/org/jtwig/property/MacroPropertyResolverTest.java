package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.impl.NodeRenderer;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.Macro;
import org.jtwig.context.model.MacroContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.reflection.MethodInvoker;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.impl.StringRenderable;
import org.jtwig.value.converter.Converter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MacroPropertyResolverTest {
    private static final String MACRO_NAME = "macroName";
    private final Position position = mock(Position.class);
    private RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private MacroPropertyResolver underTest = new MacroPropertyResolver() {
        @Override
        protected RenderContext createRenderContext(ValueContext valueContext) {
            return renderContext;
        }

        @Override
        protected RenderContext getRenderContext() {
            return renderContext;
        }
    };
    private final ArrayList<FunctionArgument> arguments = new ArrayList<>();
    private Converter converter = mock(Converter.class);

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void resolveWhenNotMacroContext() throws Exception {
        Value value = mock(Value.class);
        PropertyResolveRequest request = new PropertyResolveRequest(position, value, MACRO_NAME, arguments, converter);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenEmptyMacroContext() throws Exception {
        HashMap<String, Macro> macros = new HashMap<>();
        MacroContext macroContext = new MacroContext(macros);
        Value value = new Value(macroContext);
        PropertyResolveRequest request = new PropertyResolveRequest(position, value, MACRO_NAME, arguments, converter);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenMacroContextContainsMacro() throws Exception {
        ArrayList<String> argumentNames = new ArrayList<>();
        HashMap<String, Macro> macros = new HashMap<>();
        MacroContext macroContext = new MacroContext(macros);
        Node content = mock(Node.class);
        NodeRenderer nodeRenderer = mock(NodeRenderer.class);

        macros.put(MACRO_NAME, new Macro(argumentNames, content));
        when(renderContext.nodeRenderer()).thenReturn(nodeRenderer);
        when(nodeRenderer.render(content)).thenReturn(new StringRenderable("one", EscapeMode.NONE));
        PropertyResolveRequest request = new PropertyResolveRequest(position, new Value(macroContext), MACRO_NAME, arguments, converter);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals(result.get().getValue(), "one");
    }
}