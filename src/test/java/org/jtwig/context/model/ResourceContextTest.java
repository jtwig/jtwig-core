package org.jtwig.context.model;

import com.google.common.base.Optional;

import org.jtwig.context.values.ValueContext;
import org.jtwig.render.Renderable;
import org.jtwig.render.model.OverrideRenderable;
import org.jtwig.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ResourceContextTest {
    private final Resource resource = mock(Resource.class);
    private final HashMap<String, Macro> macros = new HashMap<>();
    private final Map<String, OverrideRenderable> blocks = new HashMap<>();
    private ValueContext valueContext = mock(ValueContext.class);
    private ResourceContext underTest = new ResourceContext(resource, macros, blocks, valueContext);

    @Before
    public void setUp() throws Exception {
        macros.clear();
        blocks.clear();
    }

    @Test
    public void registerMacro() throws Exception {
        Macro macro = mock(Macro.class);

        underTest.register("one", macro);

        assertThat(macros.get("one"), is(macro));
    }

    @Test
    public void registerRenderableWhenNoPreviouslyRegisteredWithSameKey() throws Exception {
        Renderable renderable = mock(Renderable.class);

        underTest.register("one", renderable);

        assertThat(blocks.get("one"), instanceOf(OverrideRenderable.class));
    }

    @Test
    public void registerRenderableWhenPreviouslyRegisteredWithSameKey() throws Exception {
        Renderable renderable = mock(Renderable.class);
        Renderable renderableSecond = mock(Renderable.class);

        underTest.register("one", renderable);
        underTest.register("one", renderableSecond);

        OutputStream outputStream = mock(OutputStream.class);
        blocks.get("one").accept(outputStream);

        verify(renderableSecond).accept(outputStream);
        verify(renderable, never()).accept(outputStream);
    }

    @Test
    public void merge() throws Exception {
        final OverrideRenderable renderable = mock(OverrideRenderable.class);
        Resource resource = mock(Resource.class);
        HashMap<String, Macro> macros = new HashMap<>();
        HashMap<String, OverrideRenderable> blocks = new HashMap<String, OverrideRenderable>() {{
            put("one", renderable);
        }};
        ResourceContext input = new ResourceContext(resource, macros, blocks, valueContext);

        ResourceContext result = underTest.merge(input);

        assertThat(this.blocks.get("one"), is(renderable));
    }

    @Test
    public void currentBlockWhenNoBlock() throws Exception {
        Optional<OverrideRenderable> result = underTest.currentBlock();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void currentBlockWhenNoOverrideBlock() throws Exception {
        OutputStream outputStream = mock(OutputStream.class);
        Renderable renderable = mock(Renderable.class);
        underTest.register("test", renderable);

        Optional<OverrideRenderable> result = underTest.currentBlock();

        assertThat(result.isPresent(), is(true));
        result.get().accept(outputStream);
        verify(renderable).accept(outputStream);
    }

    @Test
    public void currentBlockWhenOverrideBlock() throws Exception {
        OutputStream outputStream = mock(OutputStream.class);
        blocks.put("test", new OverrideRenderable(mock(Renderable.class)));
        Renderable renderable = mock(Renderable.class);
        underTest.register("test", renderable);

        Optional<OverrideRenderable> result = underTest.currentBlock();

        assertThat(result.isPresent(), is(true));
        result.get().accept(outputStream);
        verify(renderable).accept(outputStream);
    }

    @Test
    public void blockWhenNoBlock() throws Exception {

        Optional<Renderable> result = underTest.block("one");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void blockWhenBlock() throws Exception {
        OverrideRenderable renderable = mock(OverrideRenderable.class);
        blocks.put("one", renderable);

        Optional<Renderable> result = underTest.block("one");

        assertThat(result.isPresent(), is(true));
        assertSame(result.get(), renderable);
    }
}