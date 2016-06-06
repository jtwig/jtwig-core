package org.jtwig.integration.issues;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.property.PropertyResolveRequest;
import org.jtwig.property.PropertyResolver;
import org.jtwig.reflection.model.Value;
import org.jtwig.reflection.model.java.JavaMethod;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class Issue303Test {
    @Test
    public void retrievingUsingGenericGetMethod() throws Exception {
        JtwigModel model = JtwigModel.newModel().with("bean", new TestBean(
                ImmutableMap.<String, String>builder()
                        .put("a", "Hi")
                        .build()
        ));

        String result = JtwigTemplate.inlineTemplate("{{ bean.a }}", configuration()
                .propertyResolvers().add(new GenericGetMethodPropertyResolver()).and()
                .build())
                .render(model);

        assertThat(result, is("Hi"));
    }

    public static class TestBean {
        private final Map<String, String> values;

        public TestBean(Map<String, String> values) {
            this.values = values;
        }

        public String get (String key) {
            return values.get(key);
        }
    }

    private static class GenericGetMethodPropertyResolver implements PropertyResolver {
        @Override
        public Optional<Value> resolve(PropertyResolveRequest request) {
            Collection<JavaMethod> methods = request.getEntity().type().methods();
            for (JavaMethod method : methods) {
                if (method.numberOfArguments() == 1 && "get".equals(method.name())) {
                    try {
                        return Optional.of(new Value(method.invoke(request.getEntity().getValue(), new Object[]{request.getPropertyName()})));
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
            return Optional.absent();
        }
    }
}
