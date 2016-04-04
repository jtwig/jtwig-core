package org.jtwig.parser;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.resource.Resource;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CachedJtwigParserTest {
    private final TemplateCache cache = mock(TemplateCache.class);
    private final JtwigParser delegate = mock(JtwigParser.class);
    private final CachedJtwigParser underTest = new CachedJtwigParser(cache, delegate);

    @Test
    public void parse() throws Exception {
        Resource resource = mock(Resource.class);
        Node node = mock(Node.class);
        when(delegate.parse(resource)).thenReturn(node);
        when(cache.get(resource, delegate)).thenReturn(node);

        Node result = underTest.parse(resource);

        assertSame(result, node);
    }
}