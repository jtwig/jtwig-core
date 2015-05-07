package org.jtwig.property;

import com.google.common.base.Optional;

import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.context.impl.NodeRenderer;
import org.jtwig.context.RenderContextBuilder;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.context.model.Macro;
import org.jtwig.context.model.MacroContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.impl.StringRenderable;
import org.jtwig.value.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MacroPropertyResolverTest {
    private static final String MACRO_NAME = "macroName";
    private final Position position = mock(Position.class);
    private RenderContext renderContext = mock(RenderContext.class);
    private RenderContextBuilder renderContextBuilder = mock(RenderContextBuilder.class);
    private MacroPropertyResolver underTest = new MacroPropertyResolver() {
        @Override
        protected RenderContextBuilder renderContextBuilder() {
            return renderContextBuilder;
        }

        @Override
        protected RenderContext getRenderContext() {
            return renderContext;
        }
    };
    private final ArrayList<FunctionArgument> arguments = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void resolveWhenNotMacroContext() throws Exception {
        PropertyResolveRequest request = new PropertyResolveRequest(position, new Object(), MACRO_NAME, arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenEmptyMacroContext() throws Exception {
        HashMap<String, Macro> macros = new HashMap<>();
        MacroContext macroContext = new MacroContext(macros);
        PropertyResolveRequest request = new PropertyResolveRequest(position, macroContext, MACRO_NAME, arguments);

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
        when(renderContextBuilder.withConfiguration(any(Configuration.class))).thenReturn(renderContextBuilder);
        when(renderContextBuilder.withValueContext(any(ValueContext.class))).thenReturn(renderContextBuilder);
        when(renderContextBuilder.build()).thenReturn(renderContext);

        macros.put(MACRO_NAME, new Macro(argumentNames, content));
        when(renderContext.nodeRenderer()).thenReturn(nodeRenderer);
        when(nodeRenderer.render(content)).thenReturn(new StringRenderable("one", EscapeMode.NONE));
        PropertyResolveRequest request = new PropertyResolveRequest(position, macroContext, MACRO_NAME, arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertEquals(result.get().getValue(), "one");
    }
}