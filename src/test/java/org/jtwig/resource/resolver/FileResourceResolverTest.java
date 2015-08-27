package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jtwig.resource.FileResource;
import org.jtwig.resource.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FileResourceResolverTest {
    public static final File EXISTING_FILE = new File(FileUtils.getTempDirectory(), "example.twig");
    public static final File MISSING_FILE = new File(FileUtils.getTempDirectory(), "missing.twig");
    public static final String CONTENT = "example";
    private FileResourceResolver underTest = new FileResourceResolver();

    @Before
    public void setUp() throws Exception {
        FileUtils.write(EXISTING_FILE, CONTENT);

    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(EXISTING_FILE);
    }

    @Test
    public void resolveWhenAbsoluteExists() throws Exception {

        Optional<Resource> result = underTest.resolve(null, FileUtils.getTempDirectory().getPath() + "/example.twig");

        assertThat(result.isPresent(), is(true));
        assertThat(IOUtils.toString(result.get().content()), is(CONTENT));
    }

    @Test
    public void resolveWhenAbsoluteMissing() throws Exception {
        Optional<Resource> result = underTest.resolve(null, FileUtils.getTempDirectory().getPath() + "/missing.twig");

        assertThat(result.isPresent(), is(false));
    }



    @Test
    public void resolveWhenRelativeCurrentExists() throws Exception {
        String relativePath = "test.twig";
        File file = new File(relativePath);
        FileUtils.write(file, "content");

        Optional<Resource> result = underTest.resolve(null, relativePath);

        assertThat(result.isPresent(), is(true));
        assertThat(IOUtils.toString(result.get().content()), is("content"));

        FileUtils.forceDelete(file);
    }

    @Test
    public void resolveWhenRelativeCurrentNotExists() throws Exception {
        Optional<Resource> result = underTest.resolve(null, "testa.twig");

        assertThat(result.isPresent(), is(false));
    }


    @Test
    public void resolveWhenRelativeOtherExists() throws Exception {
        Optional<Resource> result = underTest.resolve(new FileResource(MISSING_FILE), "example.twig");

        assertThat(result.isPresent(), is(true));
        assertThat(IOUtils.toString(result.get().content()), is(CONTENT));
    }

    @Test
    public void resolveWhenRelativeOtherNotExists() throws Exception {
        Optional<Resource> result = underTest.resolve(new FileResource(EXISTING_FILE), "missing.twig");

        assertThat(result.isPresent(), is(false));
    }
}