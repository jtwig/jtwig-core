package org.jtwig.render.node;

import com.google.common.base.Optional;
import org.jtwig.model.tree.Node;
import org.jtwig.render.node.renderer.NodeRender;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class NodeRenderSelectorTest {
    private final HashMap<Class<? extends Node>, NodeRender> renderMap = new HashMap<>();
    private final NodeRenderSelector underTest = new NodeRenderSelector(renderMap);

    @Before
    public void setUp() throws Exception {
        renderMap.clear();
    }

    @Test
    public void renderForWhenNotFound() throws Exception {
        Node node = mock(Node.class);

        Optional<NodeRender> result = underTest.renderFor(node);

        assertThat(result, is(Optional.<NodeRender>absent()));
    }

    @Test
    public void renderWhenFound() throws Exception {
        Node node = mock(Node.class);
        NodeRender nodeRender = mock(NodeRender.class);

        renderMap.put(node.getClass(), nodeRender);

        Optional<NodeRender> result = underTest.renderFor(node);

        assertThat(result.get(), is(nodeRender));
    }
}