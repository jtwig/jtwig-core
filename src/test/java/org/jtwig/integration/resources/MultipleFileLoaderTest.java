package org.jtwig.integration.resources;

import org.apache.commons.io.FileUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.jtwig.resource.loader.FileResourceLoader;
import org.jtwig.resource.loader.TypedResourceLoader;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MultipleFileLoaderTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void configWithMultipleFileLoadersAnyType() throws Exception {
        File tempFile = File.createTempFile("jtwig-", ".twig");
        String parent = tempFile.getParent();
        String filename = tempFile.getName();

        FileUtils.write(tempFile, "Hello");

        String result = JtwigTemplate.inlineTemplate("{% include '" + filename + "' %}", EnvironmentConfigurationBuilder.configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(ResourceReference.FILE, new FileResourceLoader(new File(parent)))).and().and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("Hello"));
    }

    @Test
    public void configWithMultipleFileLoadersFileType() throws Exception {
        File tempFile = File.createTempFile("jtwig-", ".twig");
        String parent = tempFile.getParent();
        String filename = tempFile.getName();

        FileUtils.write(tempFile, "Hello");

        String result = JtwigTemplate.inlineTemplate("{% include 'file:" + filename + "' %}", EnvironmentConfigurationBuilder.configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(ResourceReference.FILE, new FileResourceLoader(new File(parent)))).and().and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("Hello"));
    }

    @Test
    public void configWithoutLoaderAnyType() throws Exception {
        File tempFile = File.createTempFile("jtwig-", ".twig");
        String filename = tempFile.getName();

        FileUtils.write(tempFile, "Hello");

        expectedException.expectMessage(String.format("Resource '%s' not found", filename));
        expectedException.expect(ResourceNotFoundException.class);

        JtwigTemplate.inlineTemplate("{% include '" + filename + "' %}")
                .render(JtwigModel.newModel());
    }

    @Test
    public void configWithoutLoaderFileType() throws Exception {
        File tempFile = File.createTempFile("jtwig-", ".twig");

        expectedException.expectMessage("Resource 'file:blah' not found");
        expectedException.expect(ResourceNotFoundException.class);

        JtwigTemplate.inlineTemplate("{% include 'file:blah' %}", EnvironmentConfigurationBuilder.configuration()
                .resources().resourceLoaders().add(new TypedResourceLoader(ResourceReference.FILE, new FileResourceLoader(tempFile.getParentFile()))).and().and()
                .build())
                .render(JtwigModel.newModel());
    }

    @Test
    public void configWithLoaderFileTypeNotFound() throws Exception {
        File tempFile = File.createTempFile("jtwig-", ".twig");
        String filename = tempFile.getName();

        FileUtils.write(tempFile, "Hello");

        expectedException.expectMessage(String.format("Resource 'file:%s' not found", filename));
        expectedException.expect(ResourceNotFoundException.class);

        JtwigTemplate.inlineTemplate("{% include 'file:" + filename + "' %}")
                .render(JtwigModel.newModel());
    }
}
