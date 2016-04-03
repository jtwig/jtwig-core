package org.jtwig.render.node.renderer;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matcher;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.tree.ForLoopNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.CompositeRenderable;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.context.ValueContext;
import org.jtwig.value.convert.Converter;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ForLoopNodeRenderTest {
    private ForLoopNodeRender underTest = new ForLoopNodeRender();

    @Test
    public void render() throws Exception {
        ArgumentCaptor<ValueContext> argumentCaptor = ArgumentCaptor.forClass(ValueContext.class);

        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        ForLoopNode loopNode = mock(ForLoopNode.class, RETURNS_DEEP_STUBS);
        Expression expression = mock(Expression.class);
        Object collectionValue = new Object();
        WrappedCollection wrappedCollection = mock(WrappedCollection.class);
        Object object1 = new Object();
        Renderable renderable = mock(Renderable.class);
        Node content = mock(Node.class);
        VariableExpression variableExpression = mock(VariableExpression.class);

        when(loopNode.getExpression()).thenReturn(expression);
        when(loopNode.getContent()).thenReturn(content);
        when(loopNode.getVariableExpression().getIdentifier()).thenReturn("item");
        when(loopNode.getKeyVariableExpression()).thenReturn(Optional.of(variableExpression));
        when(variableExpression.getIdentifier()).thenReturn("key");
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(collectionValue);
        when(request.getEnvironment().getValueEnvironment().getCollectionConverter().convert(collectionValue)).thenReturn(Converter.Result.defined(wrappedCollection));
        when(wrappedCollection.iterator()).thenReturn(ImmutableMap.<String, Object>builder()
                .put("key1", object1)
                .build().entrySet().iterator());
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, content)).thenReturn(renderable);

        Renderable result = underTest.render(request, loopNode);

        assertThat(result, is((Matcher) theSame(new CompositeRenderable(asList(renderable)))));
    }
}