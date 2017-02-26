package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.model.tree.visitor.NodeVisitor;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmbedNodeTest {
    private final OverrideBlockNode overrideBlockNode = mock(OverrideBlockNode.class);
    private EmbedNode underTest = new EmbedNode(mock(Position.class), asList(overrideBlockNode), mock(IncludeConfiguration.class));

    @Test
    public void testVisit() throws Exception {
        NodeVisitor nodeVisitor = mock(NodeVisitor.class);

        underTest.visit(nodeVisitor);

        verify(overrideBlockNode).visit(nodeVisitor);
    }
}