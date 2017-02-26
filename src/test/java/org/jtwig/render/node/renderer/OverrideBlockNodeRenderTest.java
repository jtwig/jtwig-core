package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.OverrideBlockNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.context.model.BlockContext;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class OverrideBlockNodeRenderTest {
    private final OverrideBlockNodeRender underTest = new OverrideBlockNodeRender();

    @Test
    public void render() throws Exception {
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        BlockContext blockContext = mock(BlockContext.class);
        OverrideBlockNode blockNode = mock(OverrideBlockNode.class);
        ResourceReference resourceReference = mock(ResourceReference.class);

        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(resourceReference);
        when(request.getRenderContext().getCurrent(BlockContext.class)).thenReturn(blockContext);

        Renderable result = underTest.render(request, blockNode);

        assertSame(EmptyRenderable.instance(), result);
    }
}