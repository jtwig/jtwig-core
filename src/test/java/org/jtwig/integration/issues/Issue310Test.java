package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.extension.Extension;
import org.jtwig.functions.FunctionRequest;
import org.jtwig.functions.JtwigFunction;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;

import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.junit.Assert.assertArrayEquals;

public class Issue310Test {
    @Test
    public void renderEncodingWithNestedConstructsTest() throws Exception {
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        JtwigModel model = JtwigModel.newModel().with("string", "The è");
        Charset outputCharset = Charset.forName("Cp858");

        JtwigTemplate.inlineTemplate("{{string}}", configuration()
                .render().withOutputCharset(outputCharset).and()
                .build()).render(model, outputStream1);

        JtwigTemplate.inlineTemplate("{% filter void %}{{string}}{% endfilter %}", configuration()
                .render().withOutputCharset(outputCharset).and()
                .extensions().add(new MyExtension()).and()
                .build()).render(model, outputStream2);

        assertArrayEquals(outputStream1.toByteArray(), outputStream2.toByteArray());
    }

    public static class MyExtension implements Extension {
        @Override
        public void configure(EnvironmentConfigurationBuilder configurationBuilder) {
            configurationBuilder.functions()
                .add(new JtwigFunction() {
                    @Override
                    public String name() {
                        return "void";
                    }

                    @Override
                    public Collection<String> aliases() {
                        return Collections.emptyList();
                    }

                    @Override
                    public Object execute(FunctionRequest request) {
                        return request.get(0);
                    }
                });
        }
    }
}
