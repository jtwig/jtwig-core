package org.jtwig.integration.issues;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class Issue210Test {
    @Test
    public void testNonUTFEncoding() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("text", "tête de bou  간편한 설치 및 사용");

        String result = JtwigTemplate
                .inlineTemplate(Charset.forName("UTF-8"), "{{ text }}", configuration()
                        .render().withOutputCharset(Charset.forName("ISO-8859-1")).and()
                        .build())
                .render(model);

        assertThat(result, is(equalTo("tÃªte de bou  ê°\u0084í\u008E¸í\u0095\u009C ì\u0084¤ì¹\u0098 ë°\u008F ì\u0082¬ì\u009A©")));
    }

    @Test
    public void testUTFEncoding() throws Exception {
        JtwigModel model = new JtwigModel();
        model.with("text", "tête de bou  간편한 설치 및 사용");

        String result = JtwigTemplate
                .inlineTemplate(Charset.forName("UTF-8"), "{{ text }}", configuration()
                        .render().withOutputCharset(Charset.forName("UTF-8")).and()
                        .build())
                .render(model);

        assertThat(result, is(equalTo("tête de bou  간편한 설치 및 사용")));
    }
}
