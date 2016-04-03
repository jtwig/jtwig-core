package org.jtwig.render.node.renderer;

import org.hamcrest.Matcher;
import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.CompositeRenderable;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CompositeNodeRenderTest {
    private CompositeNodeRender underTest = new CompositeNodeRender();

    @Test
    public void render() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        CompositeNode compositeNode = mock(CompositeNode.class);
        Renderable renderable1 = mock(Renderable.class);
        Renderable renderable2 = mock(Renderable.class);
        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);

        when(compositeNode.getNodes()).thenReturn(asList(node1, node2));
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, node1)).thenReturn(renderable1);
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, node2)).thenReturn(renderable2);

        Renderable result = underTest.render(request, compositeNode);

        Renderable expected = new CompositeRenderable(asList(renderable1, renderable2));
        assertThat(result, (Matcher<? super Renderable>) is(theSame(expected)));
    }
}