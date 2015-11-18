package org.jtwig.resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FileResourceTest {

    public static final File FILE = new File(FileUtils.getTempDirectory(), "example.twig");
    public static final String CONTENT = "example";

    @Before
    public void setUp() throws Exception {
        FileUtils.write(FILE, CONTENT);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(FILE);
    }

    @Test
    public void content() throws Exception {
        FileResource underTest = new FileResource(Charset.defaultCharset(), FILE);

        String content = IOUtils.toString(underTest.getContent());

        assertThat(content, is(CONTENT));
    }

    @Test
    public void hashCodeOfRelative() throws Exception {
        File file = new File("two");
        FileResource fileResource = new FileResource(Charset.defaultCharset(), file);
        FileResource anotherResource = new FileResource(Charset.defaultCharset(), new File(file, "../two"));

        assertThat(fileResource.hashCode(), is(anotherResource.hashCode()));
    }
}