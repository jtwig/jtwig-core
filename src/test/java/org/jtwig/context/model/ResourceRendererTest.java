package org.jtwig.context.model;

import org.jtwig.JtwigModel;
import org.jtwig.environment.Environment;
import org.jtwig.context.RenderContext;
import org.jtwig.context.impl.ResourceRenderer;
import org.jtwig.context.values.ValueContext;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResourceRendererTest {
    private final RenderContext renderContext = mock(RenderContext.class);
    private final Stack<ResourceContext> contextStack = new Stack<>();
    private final Stack<ValueContext> valueContextStack = new Stack<>();
    private final Map<String, Renderable> blocks = new HashMap<>();
    private final JtwigModel firstModel = mock(JtwigModel.class);
    private final JtwigModel model = mock(JtwigModel.class);
    private ResourceRenderer underTest = new ResourceRenderer(renderContext, contextStack, valueContextStack, blocks, model);

    @Before
    public void setUp() throws Exception {
        contextStack.clear();
        valueContextStack.clear();
        valueContextStack.push(firstModel);
    }

    @Test
    public void defineVariable() throws Exception {
        Object value = new Object();

        underTest.define("one", value);

        verify(model).add("one", value);
    }

    @Test
    public void defineMap() throws Exception {
        Object value = new Object();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("one", value);
        map.put("one1", value);

        underTest.define(map);

        verify(model).add("one", value);
        verify(model).add("one1", value);
    }

    @Test
    public void renderResourceWhenInheritModel() throws Exception {
        underTest.inheritModel(true);
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        Environment environment = mock(Environment.class);
        when(renderContext.environment()).thenReturn(environment);
        JtwigParser jtwigParser = mock(JtwigParser.class);
        when(environment.parser()).thenReturn(jtwigParser);
        when(jtwigParser.parse(resource)).thenReturn(node);
        when(node.render(renderContext)).thenReturn(renderable);

        ResourceRenderResult result = underTest.render(resource);

        assertThat(result.renderable(), is(renderable));
        assertThat(result.resource(), is(resource));
    }

    @Test
    public void renderResourceWhenNotInheritModel() throws Exception {
        underTest.inheritModel(false);
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        Environment environment = mock(Environment.class);
        when(renderContext.environment()).thenReturn(environment);
        JtwigParser jtwigParser = mock(JtwigParser.class);
        when(environment.parser()).thenReturn(jtwigParser);
        when(jtwigParser.parse(resource)).thenReturn(node);
        when(node.render(renderContext)).thenReturn(renderable);

        ResourceRenderResult result = underTest.render(resource);

        assertThat(result.renderable(), is(renderable));
        assertThat(result.resource(), is(resource));
    }
}