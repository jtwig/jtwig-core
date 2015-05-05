package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.impl.ResourceRenderer;
import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncludeNodeTest extends AbstractNodeTest {

    private final Position position = mock(Position.class);
    private final IncludeConfiguration configuration = mock(IncludeConfiguration.class, RETURNS_DEEP_STUBS);
    private final ResourceRenderer resourceRenderer = renderContext().resourceRenderer();
    private IncludeNode underTest = new IncludeNode(position, configuration);


    @Test
    public void renderWhenResourceNotExistsAndIgnoreMissing() throws Exception {
        String path = "path";
        when(configuration.getInclude().calculate(renderContext())).thenReturn(JtwigValueFactory.create(path));
        when(configuration.isIgnoreMissing()).thenReturn(true);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), eq(path))).thenReturn(Optional.<Resource>absent());

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void renderWhenResourceNotExistsAndNotIgnoreMissing() throws Exception {
        String path = "path";
        when(configuration.getInclude().calculate(renderContext())).thenReturn(JtwigValueFactory.create(path));
        when(configuration.isIgnoreMissing()).thenReturn(false);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), eq(path))).thenReturn(Optional.<Resource>absent());

        underTest.render(renderContext());
    }


    @Test
    public void renderWhenResourceExists() throws Exception {
        String path = "path";
        Resource resource = mock(Resource.class);
        ResourceRenderResult renderResult = mock(ResourceRenderResult.class, RETURNS_DEEP_STUBS);
        when(configuration.getInclude().calculate(renderContext())).thenReturn(JtwigValueFactory.create(path));
        when(configuration.isIgnoreMissing()).thenReturn(true);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), eq(path))).thenReturn(Optional.of(resource));
        when(resourceRenderer.inheritModel(anyBoolean())).thenReturn(resourceRenderer);
        when(resourceRenderer.define(anyMap())).thenReturn(resourceRenderer);
        when(resourceRenderer.render(resource)).thenReturn(renderResult);

        Renderable result = underTest.render(renderContext());

        assertThat(result, is(renderResult.renderable()));
    }
}