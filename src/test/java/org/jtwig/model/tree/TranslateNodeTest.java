package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.i18n.decorate.MessageDecorator;
import org.jtwig.model.position.Position;
import org.jtwig.render.Renderable;
import org.jtwig.value.configuration.CompatibleModeValueConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TranslateNodeTest extends AbstractNodeTest {
    private final Position position = mock(Position.class);
    private final Node content = mock(Node.class);
    private TranslateNode underTest;

    @Before
    public void setContent() throws Exception {
        when(renderContext().environment().valueConfiguration()).thenReturn(new CompatibleModeValueConfiguration());
        render(content, "content");
    }

    @Test
    public void renderWithContentOnly() throws Exception {
        underTest = new TranslateNode(position, content, null, null);
        when(renderContext().environment().renderConfiguration().currentLocaleSupplier().get()).thenReturn(Locale.ENGLISH);
        when(renderContext().environment().messageResolver()
                .resolve(eq(Locale.ENGLISH), eq("content"), any(MessageDecorator.class)))
                .thenReturn(Optional.<String>absent());

        Renderable render = underTest.render(renderContext());

        assertThat(renderResult(render), is("content"));
    }

    @Test
    public void renderWithContentAndTranslationOnly() throws Exception {
        underTest = new TranslateNode(position, content, null, null);
        when(renderContext().environment().renderConfiguration().currentLocaleSupplier().get()).thenReturn(Locale.ENGLISH);
        when(renderContext().environment().messageResolver()
                .resolve(eq(Locale.ENGLISH), eq("content"), any(MessageDecorator.class)))
                .thenReturn(Optional.of(""));

        Renderable render = underTest.render(renderContext());

        assertThat(renderResult(render), is(""));
    }
}