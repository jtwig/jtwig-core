package org.jtwig.cache;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.property.resolver.EmptyPropertyResolver;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCacheKey;
import org.jtwig.property.selection.cache.SelectionPropertyResolverPersistentCache;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.jtwig.support.IsOptional.isAbsent;
import static org.jtwig.support.IsOptional.isValue;

public class SelectionPropertyResolverPersistentCacheTest {

    private SelectionPropertyResolverPersistentCache recolverCache;
    private SelectionPropertyResolverCacheKey cacheKeyA;
    private SelectionPropertyResolverCacheKey cacheKeyB;
    private final PropertyResolver resolver = EmptyPropertyResolver.instance();

    @Before
    public void prepareTestCacheKeys() {
        ResourceReference resourceReference = ResourceReference.inline("{{ item }}");
        Position position = new Position(resourceReference, 15, 28);
        Expression expression = new VariableExpression(position, "item");

        cacheKeyA = SelectionPropertyResolverCacheKey.createFor(42, expression);
        cacheKeyB = SelectionPropertyResolverCacheKey.createFor(32, expression);
    }

    @Before
    public void createResolverCache() {
        ConcurrentHashMap<SelectionPropertyResolverCacheKey, PropertyResolver> resolverHashMap = new ConcurrentHashMap<>();
        recolverCache = new SelectionPropertyResolverPersistentCache(resolverHashMap);
    }

    @Test
    public void getCachedResolverForUnknownCacheKeyReturnsAbsent() {
        recolverCache.cacheResolver(cacheKeyA, resolver);
        assertThat(recolverCache.getCachedResolver(cacheKeyB), isAbsent());
    }

    @Test
    public void getCachedResolverForKnownCacheKeyReturnsAbsent() {
        recolverCache.cacheResolver(cacheKeyA, resolver);
        assertThat(recolverCache.getCachedResolver(cacheKeyA), isValue(resolver));
    }

}