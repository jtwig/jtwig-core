package org.jtwig.integration.expression;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.property.selection.cache.NoSelectionPropertyResolverCache;
import org.jtwig.value.context.MapValueContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.JtwigModel.newModel;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class SelectionTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void selectionWithNonExistingPropertyAndStrictModeInactive() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ asd.test }}", configuration()
                .render().withStrictMode(false).and()
                .build())
                .render(newModel().with("asd", 1));

        assertThat(result, is(""));
    }
    @Test
    public void selectionWithNonExistingPropertyAndStrictModeActive() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Cannot extract property 'test' out of 'asd'"));

        JtwigTemplate.inlineTemplate("{{ asd.test }}", configuration()
                .render().withStrictMode(true).and()
                .build())
                .render(newModel().with("asd", 1));
    }

    @Test
    public void propertyResolutionMethodGet() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.test }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("hello"));
    }

    @Test
    public void nestedPropertyResolution() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.nested.field[0] }}")
                .render(newModel().with("var", new TestClass("nani")));

        assertThat(result, is("nani"));
    }

    @Test
    public void nestedPropertyResolutionWithoutCache() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.nested.field[0] }}", configuration()
                .propertyResolver().withCache(NoSelectionPropertyResolverCache.noSelectionPropertyResolverCache()).and()
                .build())
                .render(newModel().with("var", new TestClass("nani")));

        assertThat(result, is("nani"));
    }

    @Test
    public void propertyWithValueContext() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.value }}")
                .render(newModel().with("var", new MapValueContext(ImmutableMap.<String, Object>of("value", "nani"))));

        assertThat(result, is("nani"));
    }

    @Test
    public void propertyResolutionMethodIs() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.test1 }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("hello"));
    }

    @Test
    public void propertyResolutionMethodHas() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.test2 }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("hello"));
    }

    @Test
    public void propertyResolutionMethodFails() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.test3 }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is(""));
    }

    @Test
    public void propertyResolutionMethodExactMethod() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.getTest }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("hello"));
    }

    @Test
    public void propertyResolutionPrivateField() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.privateField }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("hello"));
    }

    @Test
    public void propertyResolutionPublicField() throws Exception {

        String result = JtwigTemplate.inlineTemplate("{{ var.sum }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("0"));
    }

    @Test
    public void propertySelectionFromMap() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.sum }}")
                .render(newModel().with("var", new HashMap() {{ put("sum", 0); }}));

        assertThat(result, is("0"));
    }

    @Test
    public void methodCallSelection() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.getMerda('one', 'two') }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("[one, two]"));
    }


    @Test
    public void methodCallSelectionOneStatic() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.getMerda1('one', 'two') }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("[two]"));
    }

    @Test
    public void methodCallSelectionOneStaticNoVarArgs() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.getMerda1('one') }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is("[]"));
    }

    @Test
    public void methodCallSelectionOneStaticFewVarArgs() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.getMerda1() }}")
                .render(newModel().with("var", new TestClass("hello")));

        assertThat(result, is(""));
    }

    @Test
    public void selectionMethodCallWithStringArg() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{{ var.argCallString('test') }}")
                .render(newModel().with("var", new TestClass("test")));

        assertThat(result, is("test"));
    }

    @Test
    public void selectionMethodCallWithArguments() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% for i in [1] %}{{ var.argInteger(loop.index0) }}{% endfor %}")
                .render(newModel().with("var", new TestClass("test")));

        assertThat(result, is("0"));
    }

    public static class TestClass {
        private final String privateField;
        public final Integer sum = 0;
        private final NestedTestClass nested;

        public TestClass(String privateField) {
            this.privateField = privateField;
            this.nested = new NestedTestClass(privateField);
        }

        public String getTest () {
            return privateField;
        }

        public String getMerda (String... args) {
            return asList(args).toString();
        }

        public String getMerda1 (String ola, String... args) {
            return asList(args).toString();
        }

        public String isTest1 () {
            return privateField;
        }

        public String hasTest2 () {
            return privateField;
        }

        public String argCallString (String argument) {
            return argument;
        }

        public int argInteger (int argument) {
            return argument;
        }
    }

    public static class NestedTestClass {
        private final List<String> field;

        public NestedTestClass (String value) {
            field = ImmutableList.of(value);
        }
        public List<String> getField() {
            return field;
        }
    }
}
