package org.jtwig.context.model;

import com.google.common.base.Optional;
import org.jtwig.context.values.ValueContext;
import org.jtwig.render.RenderResult;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.OverrideRenderable;
import org.jtwig.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ResourceContextTest {
    private final Resource resource = mock(Resource.class);
    private final HashMap<String, Macro> macros = new HashMap<>();
    private final Map<String, Renderable> blocks = new HashMap<>();
    private final RenderResult renderResult = mock(RenderResult.class);
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
    public void merge() throws Exception {
        final Renderable renderable = mock(Renderable.class);
        Resource resource = mock(Resource.class);
        HashMap<String, Macro> macros = new HashMap<>();
        HashMap<String, Renderable> blocks = new HashMap<String, Renderable>() {{
            put("one", renderable);
        }};
        ResourceContext input = new ResourceContext(resource, macros, blocks, valueContext);

        ResourceContext result = underTest.merge(input);

        assertThat(this.blocks.get("one"), is(renderable));
    }

    @Test
    public void currentBlockWhenNoBlock() throws Exception {
        Optional<String> result = underTest.currentBlock();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void currentBlockWhenNoOverrideBlock() throws Exception {
        Renderable renderable = mock(Renderable.class);
        underTest.startBlock("test");

        Optional<String> result = underTest.currentBlock();

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void currentBlockWhenOverrideBlock() throws Exception {
        blocks.put("test", new OverrideRenderable(mock(Renderable.class)));
        Renderable renderable = mock(Renderable.class);
        underTest.startBlock("test");

        Optional<String> result = underTest.currentBlock();

        assertThat(result.isPresent(), is(true));
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