package org.jtwig.resource.resolver.path;

import org.jtwig.resource.exceptions.ResourceException;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.jtwig.TestUtils.universalPath;
import static org.junit.Assert.assertThat;

public class RelativePathResolverTest {
    private RelativePathResolver underTest = RelativePathResolver.instance();

    @Test
    public void isRelative() throws Exception {
        assertThat(underTest.isRelative("asd"), is(true));
        assertThat(underTest.isRelative(File.separator+"asd"), is(false));
    }

    @Test
    public void resolve() throws Exception {
        assertThat(underTest.resolve(universalPath("/parent"), "child"), is(universalPath("/child")));
        assertThat(underTest.resolve(universalPath("/test/parent"), "child"), is(universalPath("/test/child")));
        assertThat(underTest.resolve(universalPath("/test/parent/"), "child"), is(universalPath("/test/child")));
    }

    @Test(expected = ResourceException.class)
    public void resolveInvalidConicalPath() throws Exception {
        underTest.resolve(universalPath("/\u0000test/parent"), "child");
    }

    @Test
    public void isRelativeOnWindows() throws Exception {
        assertThat(underTest.isRelative("C:\\"), is(false));
        assertThat(underTest.isRelative("\\test"), is(false));
        assertThat(underTest.isRelative("one\\two"), is(true));
    }
}