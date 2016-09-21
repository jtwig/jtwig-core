package org.jtwig.integration.stackoverflow;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.escape.EscapeEngine;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

// http://stackoverflow.com/questions/37906429/jtwig-global-field-filter
public class JtwigGlobalFieldFilterTest {

    @Test
    public void example1() throws Exception {
        // define a custom escape mode with autoescape tag
        EnvironmentConfiguration configuration = configuration()
                .escape()
                .engines()
                .add("Custom", removeStrangeCharacters())
                .and()
                .and()
                .build();
        StringBuffer fieldValue = new StringBuffer()
                .append(Character.toChars(0xFF))
                .append("a");
        JtwigModel jtwigModel = JtwigModel.newModel().with("myField", fieldValue.toString());

        String result = JtwigTemplate
                .inlineTemplate("{% autoescape 'Custom' %}{{ myField }}{% endautoescape %}", configuration)
                .render(jtwigModel);

        assertThat(result, is("a"));
    }

    @Test
    public void example2() throws Exception {
        // set the custom escape mode as initial escape mode
        EnvironmentConfiguration configuration = configuration()
                .escape()
                    .withInitialEngine("Custom")
                    .engines()
                        .add("Custom", removeStrangeCharacters())
                    .and()
                .and()
                .build();
        StringBuffer fieldValue = new StringBuffer()
                .append(Character.toChars(0xFF))
                .append("a");
        JtwigModel jtwigModel = JtwigModel.newModel().with("myField", fieldValue.toString());

        String result = JtwigTemplate
                .inlineTemplate("{{ myField }}", configuration)
                .render(jtwigModel);

        assertThat(result, is("a"));
    }

    private EscapeEngine removeStrangeCharacters() {
        return new EscapeEngine() {
            @Override
            public String escape(String input) {
                return input.replaceAll("[^\\x00-\\x7F]", "");
            }
        };
    }
}
