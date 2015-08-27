package org.jtwig.model.tree;

import jdk.nashorn.internal.ir.ForNode;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.junit.Test;

import java.io.OutputStream;
import java.util.Arrays;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ForNodeTest {
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final OutputStream outputStream = mock(OutputStream.class);
    private final Position position = mock(Position.class);
    private final Node innerNode = mock(Node.class);
    private final Expression listExpression = mock(Expression.class);
    private ForNode underTest;

    @Test
    public void renderWhenNoObjects() throws Exception {
        Object value = mock(Object.class);
//        underTest = new ForNode(position, asList(innerNode), "variable", listExpression);
//        when(listExpression.calculate(renderContext)).thenReturn(value);
//        when(renderContext.iterableFor(value)).thenReturn(emptyList());

//        underTest.render(renderContext);

//        verify(renderContext, never()).render(innerNode);
    }

    @Test
    public void renderWhenObjects() throws Exception {
        Object value = mock(Object.class);
//        underTest = new ForNode(position, asList(innerNode), "variable", listExpression);
//        when(listExpression.calculate(renderContext)).thenReturn(value);
//        when(renderContext.iterableFor(value)).thenReturn(Arrays.<Object>asList(1, 2));

//        underTest.render(renderContext);

//        verify(renderContext, times(2)).render(innerNode);
    }
}