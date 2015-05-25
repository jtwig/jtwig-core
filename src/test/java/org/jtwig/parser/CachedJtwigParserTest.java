package org.jtwig.parser;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jtwig.model.tree.Node;
import org.jtwig.resource.Resource;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CachedJtwigParserTest {
    private final Cache<Resource, Node> cache = CacheBuilder.<Resource, Node>newBuilder().build();
    private final JtwigParser delegate = mock(JtwigParser.class);
    private final CachedJtwigParser underTest = new CachedJtwigParser(cache, delegate);

    @Test
    public void parseWhenCacheMiss() throws Exception {
        Resource resource = mock(Resource.class);
        Node node = mock(Node.class);
        when(delegate.parse(resource)).thenReturn(node);

        Node result = underTest.parse(resource);

        assertSame(result, node);
        verify(delegate).parse(resource);
    }

    @Test
    public void parseWhenCacheHit() throws Exception {
        Resource resource = mock(Resource.class);
        Node node = mock(Node.class);
        when(delegate.parse(resource)).thenReturn(node);

        underTest.parse(resource);
        Node result = underTest.parse(resource);

        assertSame(result, node);
        verify(delegate, times(1)).parse(resource);
    }

    @Test(expected = ParseException.class)
    public void parseWhenCacheExecutionException() throws Exception {
        Resource resource = mock(Resource.class);
        Node node = mock(Node.class);
        when(delegate.parse(resource)).thenThrow(ExecutionException.class);

        underTest.parse(resource);

    }
}