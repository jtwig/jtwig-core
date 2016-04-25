package org.jtwig.parser;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.resource.reference.ResourceReference;
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
        Environment environment = mock(Environment.class);
        ResourceReference resource = mock(ResourceReference.class);
        Node node = mock(Node.class);
        when(delegate.parse(environment, resource)).thenReturn(node);
        when(cache.get(delegate, environment, resource)).thenReturn(node);

        Node result = underTest.parse(environment, resource);

        assertSame(result, node);
    }
}