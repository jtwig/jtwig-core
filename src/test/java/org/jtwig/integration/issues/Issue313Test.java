package org.jtwig.integration.issues;

import org.apache.commons.io.FileUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Issue313Test {
    @Test
    public void allowingForCurrentDirectoryRelativePaths() throws Exception {
        File parentDirectory = FileUtils.getTempDirectory();
        File subDirectory = new File(parentDirectory, "temp");
        subDirectory.mkdirs();

        File file1 = new File(parentDirectory, "file1.twig");
        File file2 = new File(subDirectory, "file2.twig");

        FileUtils.write(file1, "{% include './temp/file2.twig' %}");
        FileUtils.write(file2, "{{ var }}");

        String result = JtwigTemplate.fileTemplate(file1)
                .render(JtwigModel.newModel()
                        .with("var", "Hi")
                );

        assertThat(result, is("Hi"));
    }
}
