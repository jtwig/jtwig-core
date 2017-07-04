package org.jtwig.integration.expression;

import com.google.common.collect.ImmutableList;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.property.resolver.PropertyResolver;
import org.jtwig.property.selection.cache.SelectionPropertyResolverCacheKey;
import org.jtwig.property.selection.cache.SelectionPropertyResolverPersistentCache;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PolymorphicVariableTest {

	private List<ParentClass> list;
	private EnvironmentConfiguration configuration;

	private abstract class ParentClass {
		public abstract String getType();
	}

	private class ConcreteClassA extends ParentClass {
		public String getType() {
			return "A";
		}
	}

	private class ConcreteClassB extends ParentClass {
		public String getType() {
			return "B";
		}
	}

	@Before
	public void setupTestInstances() {
		list = ImmutableList.of(
				new ConcreteClassA(),
				new ConcreteClassB(),
				new ConcreteClassA()
		);
	}

	@Before
	public void setupConfiguration() {
		final ConcurrentHashMap<SelectionPropertyResolverCacheKey, PropertyResolver> resolverHashMap = new ConcurrentHashMap<>();
		final SelectionPropertyResolverPersistentCache resolverCache = new SelectionPropertyResolverPersistentCache(resolverHashMap);

		configuration = EnvironmentConfigurationBuilder.configuration()
				.propertyResolver().withCache(resolverCache).and()
				.build();
	}

	@Test
	public void testPolymorphicVariableIsAcceessibleInPresenceOfPersistantResolverCache() {
		String result = JtwigTemplate.inlineTemplate("{% for item in list %}{{ item.type }}{% endfor %}", configuration)
				.render(JtwigModel.newModel().with("list", list));

		assertThat(result, is("ABA"));
	}
}
