package org.jtwig.integration.issues;

import org.apache.commons.io.FileUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.jtwig.util.EscapeUtils.escapeJtwig;
import static org.junit.Assert.assertThat;

public class Issue294Test {
    @Test
    public void jtwigSupportsAbsolutePaths() throws Exception {
        File baseDir = new File(new File(FileUtils.getTempDirectory(), "jtwig"), "test");
        baseDir.mkdirs();
        File child = new File(baseDir, "child.twig");
        FileUtils.write(child, "{{ var }}");

        File parent = File.createTempFile("jtwig", "test");
        FileUtils.write(parent, "{% extends '"+ escapeJtwig(baseDir.getAbsolutePath() + File.separator)+ "child.twig' %}");

        String result = JtwigTemplate.fileTemplate(parent).render(JtwigModel.newModel()
                .with("var", "Hi"));

        assertThat(result, is("Hi"));
    }
}
