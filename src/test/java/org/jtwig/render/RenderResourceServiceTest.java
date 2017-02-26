package org.jtwig.render;

import org.jtwig.environment.Environment;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.context.ValueContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RenderResourceServiceTest {
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private RenderResourceService underTest = new RenderResourceService();
    private RenderRequest renderRequest = mock(RenderRequest.class, RETURNS_DEEP_STUBS);

    @Before
    public void setUp() throws Exception {
        when(renderRequest.getEnvironment()).thenReturn(environment);
    }

    @Test
    public void renderHappy() throws Exception {
        EscapeEngine initialEscapeMode = HtmlEscapeEngine.instance();
        Node node = mock(Node.class);
        ResourceReference resource = mock(ResourceReference.class);
        Renderable expectedRenderable = mock(Renderable.class);
        RenderResourceRequest renderResourceRequest = mock(RenderResourceRequest.class, RETURNS_DEEP_STUBS);
        ResourceMetadata resourceMetadata = mock(ResourceMetadata.class);

        when(environment.getParser().parse(environment, resource)).thenReturn(node);
        when(environment.getEscapeEnvironment().getInitialEscapeEngine()).thenReturn(initialEscapeMode);
        when(renderResourceRequest.getResource()).thenReturn(resource);
        when(renderResourceRequest.isNewBlockContext()).thenReturn(false);
        when(renderResourceRequest.isNewValueContext()).thenReturn(true);
        when(renderResourceRequest.getIncludeModel()).thenReturn(WrappedCollection.empty());
        when(renderRequest.getRenderContext().getCurrent(ValueContext.class)).thenReturn(mock(ValueContext.class));
        when(environment.getRenderEnvironment().getRenderNodeService().render(renderRequest, node))
                .thenReturn(expectedRenderable);
        when(environment.getResourceEnvironment().getResourceService().loadMetadata(resource)).thenReturn(resourceMetadata);
        when(resourceMetadata.exists()).thenReturn(true);

        Renderable renderable = underTest.render(renderRequest, renderResourceRequest);

        assertSame(expectedRenderable, renderable);
    }

    @Test
    public void renderWithNewBlockContext() throws Exception {
        EscapeEngine initialEscapeMode = HtmlEscapeEngine.instance();
        Node node = mock(Node.class);
        ResourceReference resource = mock(ResourceReference.class);
        Renderable expectedRenderable = mock(Renderable.class);
        RenderResourceRequest renderResourceRequest = mock(RenderResourceRequest.class, RETURNS_DEEP_STUBS);

        when(renderRequest.getEnvironment().getParser().parse(environment, resource)).thenReturn(node);
        when(renderRequest.getEnvironment().getEscapeEnvironment().getInitialEscapeEngine()).thenReturn(initialEscapeMode);
        when(renderRequest.getRenderContext().getCurrent(ValueContext.class)).thenReturn(mock(ValueContext.class));
        when(renderResourceRequest.getResource()).thenReturn(resource);
        when(renderResourceRequest.isNewBlockContext()).thenReturn(true);
        when(renderResourceRequest.isNewValueContext()).thenReturn(true);
        when(renderResourceRequest.getIncludeModel()).thenReturn(WrappedCollection.empty());
        when(renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService().render(renderRequest, node))
                .thenReturn(expectedRenderable);

        Renderable renderable = underTest.render(renderRequest, renderResourceRequest);

        assertSame(expectedRenderable, renderable);
    }

    @Test
    public void renderWithOldValueContext() throws Exception {
        EscapeEngine initialEscapeMode = HtmlEscapeEngine.instance();
        Node node = mock(Node.class);
        ResourceReference resource = mock(ResourceReference.class);
        Renderable expectedRenderable = mock(Renderable.class);
        RenderResourceRequest renderResourceRequest = mock(RenderResourceRequest.class, RETURNS_DEEP_STUBS);
        ValueContext valueContext = mock(ValueContext.class, "Parent Context");

        when(renderRequest.getEnvironment().getParser().parse(environment, resource)).thenReturn(node);
        when(renderRequest.getEnvironment().getEscapeEnvironment().getInitialEscapeEngine()).thenReturn(initialEscapeMode);
        when(renderResourceRequest.getResource()).thenReturn(resource);
        when(renderResourceRequest.isNewBlockContext()).thenReturn(true);
        when(renderResourceRequest.isNewValueContext()).thenReturn(false);
        when(renderResourceRequest.getIncludeModel()).thenReturn(WrappedCollection.empty());
        when(renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService().render(renderRequest, node))
                .thenReturn(expectedRenderable);
        when(renderRequest.getRenderContext().getCurrent(ValueContext.class)).thenReturn(valueContext);

        Renderable renderable = underTest.render(renderRequest, renderResourceRequest);

        assertSame(expectedRenderable, renderable);
    }
}