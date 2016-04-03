package org.jtwig.render;

import org.jtwig.model.tree.Node;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.render.context.model.EscapeMode;
import org.jtwig.render.context.model.MacroDefinitionContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.context.IsolateParentValueContext;
import org.jtwig.value.context.MapValueContext;
import org.jtwig.value.context.ValueContext;
import org.junit.Test;

import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RenderResourceServiceTest {
    private RenderResourceService underTest = new RenderResourceService();
    private RenderRequest renderRequest = mock(RenderRequest.class, RETURNS_DEEP_STUBS);

    @Test
    public void renderHappy() throws Exception {
        EscapeMode initialEscapeMode = EscapeMode.HTML;
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable expectedRenderable = mock(Renderable.class);
        RenderResourceRequest renderResourceRequest = mock(RenderResourceRequest.class, RETURNS_DEEP_STUBS);

        when(renderRequest.getEnvironment().getParser().parse(resource)).thenReturn(node);
        when(renderRequest.getEnvironment().getRenderEnvironment().getInitialEscapeMode()).thenReturn(initialEscapeMode);
        when(renderResourceRequest.getResource()).thenReturn(resource);
        when(renderResourceRequest.isNewBlockContext()).thenReturn(false);
        when(renderResourceRequest.isNewValueContext()).thenReturn(true);
        when(renderResourceRequest.getIncludeModel()).thenReturn(WrappedCollection.empty());
        when(renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService().render(renderRequest, node))
                .thenReturn(expectedRenderable);

        Renderable renderable = underTest.render(renderRequest, renderResourceRequest);

        assertSame(expectedRenderable, renderable);
        verify(renderRequest.getRenderContext().getBlockContext(), never()).start(any(BlockContext.class));
        verify(renderRequest.getRenderContext().getBlockContext(), never()).end();
        verify(renderRequest.getRenderContext().getValueContext()).start(argThat(theSame(MapValueContext.newContext())));
        verify(renderRequest.getRenderContext().getValueContext()).end();
        verify(renderRequest.getRenderContext().getResourceContext()).start(resource);
        verify(renderRequest.getRenderContext().getResourceContext()).end();
        verify(renderRequest.getRenderContext().getEscapeModeContext()).start(initialEscapeMode);
        verify(renderRequest.getRenderContext().getEscapeModeContext()).end();
        verify(renderRequest.getRenderContext().getMacroDefinitionContext(), never()).start(any(MacroDefinitionContext.class));
        verify(renderRequest.getRenderContext().getMacroDefinitionContext(), never()).end();

    }

    @Test
    public void renderWithNewBlockContext() throws Exception {
        EscapeMode initialEscapeMode = EscapeMode.HTML;
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable expectedRenderable = mock(Renderable.class);
        RenderResourceRequest renderResourceRequest = mock(RenderResourceRequest.class, RETURNS_DEEP_STUBS);

        when(renderRequest.getEnvironment().getParser().parse(resource)).thenReturn(node);
        when(renderRequest.getEnvironment().getRenderEnvironment().getInitialEscapeMode()).thenReturn(initialEscapeMode);
        when(renderResourceRequest.getResource()).thenReturn(resource);
        when(renderResourceRequest.isNewBlockContext()).thenReturn(true);
        when(renderResourceRequest.isNewValueContext()).thenReturn(true);
        when(renderResourceRequest.getIncludeModel()).thenReturn(WrappedCollection.empty());
        when(renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService().render(renderRequest, node))
                .thenReturn(expectedRenderable);

        Renderable renderable = underTest.render(renderRequest, renderResourceRequest);

        assertSame(expectedRenderable, renderable);
        verify(renderRequest.getRenderContext().getBlockContext()).start(argThat(theSame(BlockContext.newContext())));
        verify(renderRequest.getRenderContext().getBlockContext()).end();
        verify(renderRequest.getRenderContext().getValueContext()).start(argThat(theSame(MapValueContext.newContext())));
        verify(renderRequest.getRenderContext().getValueContext()).end();
        verify(renderRequest.getRenderContext().getResourceContext()).start(resource);
        verify(renderRequest.getRenderContext().getResourceContext()).end();
        verify(renderRequest.getRenderContext().getEscapeModeContext()).start(initialEscapeMode);
        verify(renderRequest.getRenderContext().getEscapeModeContext()).end();
        verify(renderRequest.getRenderContext().getMacroDefinitionContext(), never()).start(any(MacroDefinitionContext.class));
        verify(renderRequest.getRenderContext().getMacroDefinitionContext(), never()).end();
    }

    @Test
    public void renderWithOldValueContext() throws Exception {
        EscapeMode initialEscapeMode = EscapeMode.HTML;
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable expectedRenderable = mock(Renderable.class);
        RenderResourceRequest renderResourceRequest = mock(RenderResourceRequest.class, RETURNS_DEEP_STUBS);
        ValueContext valueContext = mock(ValueContext.class, "Parent Context");

        when(renderRequest.getRenderContext().getValueContext().getCurrent()).thenReturn(valueContext);
        when(renderRequest.getEnvironment().getParser().parse(resource)).thenReturn(node);
        when(renderRequest.getEnvironment().getRenderEnvironment().getInitialEscapeMode()).thenReturn(initialEscapeMode);
        when(renderResourceRequest.getResource()).thenReturn(resource);
        when(renderResourceRequest.isNewBlockContext()).thenReturn(true);
        when(renderResourceRequest.isNewValueContext()).thenReturn(false);
        when(renderResourceRequest.getIncludeModel()).thenReturn(WrappedCollection.empty());
        when(renderRequest.getEnvironment().getRenderEnvironment().getRenderNodeService().render(renderRequest, node))
                .thenReturn(expectedRenderable);

        Renderable renderable = underTest.render(renderRequest, renderResourceRequest);

        assertSame(expectedRenderable, renderable);
        verify(renderRequest.getRenderContext().getBlockContext()).start(argThat(theSame(BlockContext.newContext())));
        verify(renderRequest.getRenderContext().getBlockContext()).end();
        verify(renderRequest.getRenderContext().getValueContext()).start(argThat(theSame(new IsolateParentValueContext(valueContext, MapValueContext.newContext()))));
        verify(renderRequest.getRenderContext().getValueContext()).end();
        verify(renderRequest.getRenderContext().getResourceContext()).start(resource);
        verify(renderRequest.getRenderContext().getResourceContext()).end();
        verify(renderRequest.getRenderContext().getEscapeModeContext()).start(initialEscapeMode);
        verify(renderRequest.getRenderContext().getEscapeModeContext()).end();
        verify(renderRequest.getRenderContext().getMacroDefinitionContext(), never()).start(any(MacroDefinitionContext.class));
        verify(renderRequest.getRenderContext().getMacroDefinitionContext(), never()).end();
    }
}