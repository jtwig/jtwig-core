package org.jtwig.integration.expression;

import org.jtwig.JtwigTemplate;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.integration.AbstractIntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
                .withStrictMode(false)
                .build())
                .render(newModel().with("asd", 1));

        assertThat(result, is(""));
    }
    @Test
    public void selectionWithNonExistingPropertyAndStrictModeActive() throws Exception {
        expectedException.expect(CalculationException.class);
        expectedException.expectMessage(containsString("Impossible to access an attribute 'test' on '1'"));

        JtwigTemplate.inlineTemplate("{{ asd.test }}", configuration()
                .withStrictMode(true)
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

    public static class TestClass {
        private final String privateField;
        public final Integer sum = 0;

        public TestClass(String privateField) {
            this.privateField = privateField;
        }

        public String getTest () {
            return privateField;
        }

        public String isTest1 () {
            return privateField;
        }

        public String hasTest2 () {
            return privateField;
        }
    }
}
