package org.jtwig.integration.property;

import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.exceptions.CalculationException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MapPropertySelectionTest {
    @Test
    public void resolveMapPropertyWhenNotPresent() {
        String result = JtwigTemplate.inlineTemplate("{{ value.test }}")
                .render(JtwigModel.newModel().with("value", ImmutableMap.builder().build()));

        assertThat(result, is(""));
    }

    @Test(expected = CalculationException.class)
    public void resolveMapPropertyWhenNotPresentStrictMode() {
        JtwigTemplate.inlineTemplate("{{ value.test }}", EnvironmentConfigurationBuilder.configuration()
                .render()
                .withStrictMode(true)
                .and()
                .build())
                .render(JtwigModel.newModel().with("value", ImmutableMap.builder().build()));
    }
}
