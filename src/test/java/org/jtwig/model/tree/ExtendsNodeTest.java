package org.jtwig.model.tree;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.visitor.NodeVisitor;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExtendsNodeTest {
    Node node = mock(Node.class);
    ExtendsNode underTest = new ExtendsNode(mock(Position.class), mock(Expression.class), asList(node));

    @Test
    public void visitTest() throws Exception {
        NodeVisitor nodeConsumer = mock(NodeVisitor.class);

        underTest.visit(nodeConsumer);

        verify(node).visit(nodeConsumer);
    }
}