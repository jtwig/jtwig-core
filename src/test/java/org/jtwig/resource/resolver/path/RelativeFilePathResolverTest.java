package org.jtwig.resource.resolver.path;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RelativeFilePathResolverTest {
    private RelativeFilePathResolver underTest = RelativeFilePathResolver.instance();

    @Test
    public void isRelative() throws Exception {
        assertFalse(underTest.isRelative(new File("test").getAbsolutePath()));
        assertTrue(underTest.isRelative(new File("test").getPath()));
    }

    @Test
    public void resolve() throws Exception {
        String result = underTest.resolve("test", "test2");
        assertThat(result, is(new File("test2").getPath()));
    }
}