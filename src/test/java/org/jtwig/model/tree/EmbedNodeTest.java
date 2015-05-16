package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.impl.ResourceRenderer;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.NamedValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class EmbedNodeTest extends AbstractNodeTest {
    private final Position position = mock(Position.class);
    private final ArrayList<OverrideBlockNode> nodes = new ArrayList<>();
    private final Expression includeExpression = mock(Expression.class);
    private final IncludeConfiguration includeConfiguration = mock(IncludeConfiguration.class, RETURNS_DEEP_STUBS);
    private final ResourceRenderer resourceRenderer = renderContext().resourceRenderer();
    private final Resource resource = mock(Resource.class);
    private EmbedNode underTest = new EmbedNode(position, nodes, includeConfiguration);

    @Before
    public void setUp() throws Exception {
        nodes.clear();
    }

    @Test
    public void renderWithNonExistingResourceWhenIgnoreMissing() throws Exception {
        when(includeConfiguration.isIgnoreMissing()).thenReturn(true);
        when(includeExpression.calculate(renderContext())).thenReturn(JtwigValueFactory.value("test", NamedValueConfiguration.COMPATIBLE_MODE));
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.<Resource>absent());

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void renderWithNonExistingResourceWhenNotIgnoringMissing() throws Exception {
        when(includeExpression.calculate(renderContext())).thenReturn(JtwigValueFactory.value("test", NamedValueConfiguration.COMPATIBLE_MODE));
        when(includeConfiguration.getInclude()).thenReturn(includeExpression);
        when(includeConfiguration.isIgnoreMissing()).thenReturn(false);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.<Resource>absent());
        when(resourceRenderer.inheritModel(anyBoolean())).thenReturn(resourceRenderer);
        when(resourceRenderer.define(anyMap())).thenReturn(resourceRenderer);

        underTest.render(renderContext());
    }

    @Test
    public void renderWithExistingResourceWhenIgnoreMissing() throws Exception {
        when(includeExpression.calculate(renderContext())).thenReturn(JtwigValueFactory.value("test", NamedValueConfiguration.COMPATIBLE_MODE));
        when(includeConfiguration.getInclude()).thenReturn(includeExpression);
        when(includeConfiguration.isIgnoreMissing()).thenReturn(true);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.<Resource>absent());
        when(resourceRenderer.inheritModel(anyBoolean())).thenReturn(resourceRenderer);
        when(resourceRenderer.define(anyMap())).thenReturn(resourceRenderer);

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }

    @Test
    public void renderWithExistingResourceNotIgnoreMissing() throws Exception {
        when(includeExpression.calculate(renderContext())).thenReturn(JtwigValueFactory.value("test", NamedValueConfiguration.COMPATIBLE_MODE));
        when(includeConfiguration.getInclude()).thenReturn(includeExpression);
        when(includeConfiguration.isIgnoreMissing()).thenReturn(false);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.of(resource));
        when(resourceRenderer.inheritModel(anyBoolean())).thenReturn(resourceRenderer);
        when(resourceRenderer.define(anyMap())).thenReturn(resourceRenderer);

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }
}