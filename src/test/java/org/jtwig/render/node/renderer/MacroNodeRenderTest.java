package org.jtwig.render.node.renderer;

import org.jtwig.model.tree.MacroNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class MacroNodeRenderTest {
    private MacroNodeRender underTest = new MacroNodeRender();

    @Test
    public void renderTest() throws Exception {
        RenderRequest renderRequest = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        MacroNode macroNode = mock(MacroNode.class, RETURNS_DEEP_STUBS);

        Renderable result = underTest.render(renderRequest, macroNode);

        assertSame(EmptyRenderable.instance(), result);
    }
}