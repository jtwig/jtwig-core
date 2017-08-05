package org.jtwig.render.node.renderer;

import org.jtwig.environment.Environment;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.IncludeNode;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.RenderResourceRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.EmptyRenderable;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.jtwig.value.WrappedCollection;
import org.jtwig.value.convert.Converter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsString;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class IncludeNodeRenderTest {
    private IncludeNodeRender underTest = new IncludeNodeRender();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void renderPathNotFound() throws Exception {
        String path = "path";
        IncludeNode includeNode = mock(IncludeNode.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression resourceExpression = mock(Expression.class);
        ResourceReference currentResource = mock(ResourceReference.class);
        ResourceReference newResource = mock(ResourceReference.class);

        when(includeNode.isIgnoreMissing()).thenReturn(false);
        when(includeNode.getResourceExpression()).thenReturn(resourceExpression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, resourceExpression)).thenReturn(path);
        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(currentResource);
        when(environment.getResourceEnvironment().getResourceService().resolve(currentResource, path)).thenReturn(newResource);

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(containsString("Resource 'path' (resolved to 'null') not found"));

        underTest.render(request, includeNode);
    }

    @Test
    public void renderNotFoundIgnore() throws Exception {
        String path = "path";
        IncludeNode includeNode = mock(IncludeNode.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression resourceExpression = mock(Expression.class);
        ResourceReference currentResource = mock(ResourceReference.class);
        ResourceReference newResource = mock(ResourceReference.class);

        when(includeNode.isIgnoreMissing()).thenReturn(true);
        when(includeNode.getResourceExpression()).thenReturn(resourceExpression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, resourceExpression)).thenReturn(path);
        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(currentResource);
        when(environment.getResourceEnvironment().getResourceService().resolve(currentResource, path)).thenReturn(newResource);

        Renderable result = underTest.render(request, includeNode);

        assertSame(EmptyRenderable.instance(), result);
    }

    @Test
    public void renderFound() throws Exception {
        String path = "path";
        WrappedCollection empty = WrappedCollection.empty();
        boolean inherit = true;
        IncludeNode includeNode = mock(IncludeNode.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
        Expression resourceExpression = mock(Expression.class);
        ResourceReference currentResource = mock(ResourceReference.class);
        ResourceReference resource = mock(ResourceReference.class);
        Expression mapExpression = mock(Expression.class);
        Renderable renderable = mock(Renderable.class);
        ResourceMetadata resourceMetadata = mock(ResourceMetadata.class);
        Object mapValue = new Object();
        String relativePath = "relativePath";

        when(includeNode.isIgnoreMissing()).thenReturn(true);
        when(includeNode.isInheritModel()).thenReturn(inherit);
        when(includeNode.getResourceExpression()).thenReturn(resourceExpression);
        when(includeNode.getMapExpression()).thenReturn(mapExpression);
        when(request.getEnvironment()).thenReturn(environment);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, resourceExpression)).thenReturn(path);
        when(request.getRenderContext().getCurrent(ResourceReference.class)).thenReturn(currentResource);
        when(environment.getValueEnvironment().getStringConverter().convert(path)).thenReturn(relativePath);
        when(environment.getResourceEnvironment().getResourceService().resolve(currentResource, path, environment.getValueEnvironment())).thenReturn(resource);
        when(environment.getRenderEnvironment().getCalculateExpressionService().calculate(request, mapExpression)).thenReturn(mapValue);
        when(environment.getValueEnvironment().getCollectionConverter().convert(mapValue)).thenReturn(Converter.Result.defined(empty));
        when(environment.getRenderEnvironment().getRenderResourceService().render(eq(request), argThat(theSame(new RenderResourceRequest(resource, true, !inherit, empty))))).thenReturn(renderable);
        when(environment.getResourceEnvironment().getResourceService().loadMetadata(resource)).thenReturn(resourceMetadata);
        when(resourceMetadata.exists()).thenReturn(true);
        when(resource.getPath()).thenReturn("path");

        Renderable result = underTest.render(request, includeNode);

        assertSame(renderable, result);
    }
}