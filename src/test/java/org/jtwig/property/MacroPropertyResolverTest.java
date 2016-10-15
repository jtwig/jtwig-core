package org.jtwig.property;

import com.google.common.base.Optional;
import org.hamcrest.CoreMatchers;
import org.jtwig.environment.Environment;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.context.model.Macro;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.render.context.model.RenderContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class MacroPropertyResolverTest {
    private static final String MACRO_NAME = "macroName";
    private final Position position = mock(Position.class);
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private MacroPropertyResolver underTest = new MacroPropertyResolver(null);
    private final ArrayList<Object> arguments = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }


    @Test
    public void resolveWithNullEntity() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        given(request.getArguments()).willReturn(Collections.emptyList());
        given(request.getEntity()).willReturn(null);

        Optional<Value> result = underTest.resolve(request);

        Assert.assertThat(result.isPresent(), CoreMatchers.is(false));
    }

    @Test
    public void resolveWithNullValue() throws Exception {
        PropertyResolveRequest request = mock(PropertyResolveRequest.class);
        given(request.getArguments()).willReturn(Collections.emptyList());
        given(request.getEntity()).willReturn(new Value(null));

        Optional<Value> result = underTest.resolve(request);

        Assert.assertThat(result.isPresent(), CoreMatchers.is(false));
    }

    @Test
    public void resolveWhenNotMacroContext() throws Exception {
        Value value = mock(Value.class);
        PropertyResolveRequest request = new PropertyResolveRequest(renderContext, environment, position, value, MACRO_NAME, arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenEmptyMacroContext() throws Exception {
        MacroDefinitionContext macros = new MacroDefinitionContext(new HashMap<String, Macro>());
        PropertyResolveRequest request = new PropertyResolveRequest(renderContext, environment, position, new Value(macros), MACRO_NAME, arguments);

        Optional<Value> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }
}