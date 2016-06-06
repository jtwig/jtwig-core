package org.jtwig.integration.issues;

import org.apache.commons.io.FileUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class Issue304Test {
    @Test
    public void inputEncodingTest() throws Exception {
        String originalText = "Olá";

        File tempFile = File.createTempFile("test", "jtwig");
        FileUtils.write(tempFile, originalText, Charset.forName("ISO-8859-1"));

        String result = JtwigTemplate.fileTemplate(tempFile, configuration()
                .resources().withDefaultInputCharset(Charset.forName("ISO-8859-1")).and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is(equalTo(originalText)));
    }
}
