package org.jtwig;

import org.apache.commons.io.FileUtils;
import org.jtwig.environment.Environment;
import org.jtwig.escape.HtmlEscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.impl.StringRenderable;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class JtwigTemplateTest {
    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private final ResourceReference resource = mock(ResourceReference.class);
    private final JtwigTemplate underTest = new JtwigTemplate(environment, resource);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void render() throws Exception {
        String exampleOut = "exampleOut";
        ArgumentCaptor<RenderRequest> renderRequestArgumentCaptor = ArgumentCaptor.forClass(RenderRequest.class);
        JtwigModel model = mock(JtwigModel.class);
        Node node = mock(Node.class);

        when(environment.getParser().parse(environment, resource)).thenReturn(node);
        when(environment.getEscapeEnvironment().getInitialEscapeEngine()).thenReturn(HtmlEscapeEngine.instance());
        when(environment.getRenderEnvironment().getRenderNodeService().render(renderRequestArgumentCaptor.capture(), eq(node))).thenReturn(new StringRenderable(exampleOut, NoneEscapeEngine.instance()));

        String result = underTest.render(model);

        assertThat(result, is(exampleOut));
        RenderRequest renderRequest = renderRequestArgumentCaptor.getValue();

        assertThat(renderRequest.getEnvironment(), is(environment));
    }


    @Test
    public void classpathLocation() throws Exception {
        expectedException.expectMessage(containsString("Resource '/test' not found"));

        JtwigTemplate.classpathTemplate("/test", configuration().build()).render(JtwigModel.newModel());
    }

    @Test
    public void classpathLocation2() throws Exception {
        expectedException.expectMessage(containsString("Resource 'classpath:/test' not found"));

        JtwigTemplate.classpathTemplate("classpath:/test", configuration().build()).render(JtwigModel.newModel());
    }


    @Test
    public void filenameTemplateTest() throws Exception {
        assertNotNull(JtwigTemplate.fileTemplate("path"));
    }


    @Test
    public void filenameTemplateTest1() throws Exception {
        assertNotNull(JtwigTemplate.fileTemplate("path", configuration().build()));
    }


    @Test
    public void filenameTemplateTest2() throws Exception {
        assertNotNull(JtwigTemplate.fileTemplate(new File("")));
    }


    @Test
    public void filenameTemplateTest3() throws Exception {
        assertNotNull(JtwigTemplate.fileTemplate(new File(""), configuration().build()));
    }

    @Test
    public void fileTemplateRead() throws Exception {
        File tempFile = Files.createTempFile("jtwig", "template").toFile();
        FileUtils.write(tempFile, "Hi {{ name }}!");

        String result = JtwigTemplate.fileTemplate(tempFile).render(JtwigModel.newModel().with("name", "World"));

        assertThat(result, is("Hi World!"));
    }

    @Test
    public void fileNotExists() throws Exception {
        expectedException.expectMessage(containsString("Resource 'file:/nonSensePath' (resolved to 'file:/nonSensePath') not found"));

        JtwigTemplate.inlineTemplate("{% include 'file:/nonSensePath' %}").render(JtwigModel.newModel().with("name", "World"));
    }
}