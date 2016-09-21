package org.jtwig.resource.resolver.path;

import org.jtwig.resource.exceptions.ResourceException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RelativePathResolverTest {
    private RelativePathResolver underTest = RelativePathResolver.instance();

    @Test
    public void isRelative() throws Exception {
        assertThat(underTest.isRelative("asd"), is(true));
        assertThat(underTest.isRelative("/asd"), is(false));
    }

    @Test
    public void resolve() throws Exception {
        assertThat(underTest.resolve("/parent", "child"), is("/child"));
        assertThat(underTest.resolve("parent", "../child"), is("../child"));
        assertThat(underTest.resolve("/test/parent", "child"), is("/test/child"));
        assertThat(underTest.resolve("/test/parent/", "child"), is("/test/child"));
    }

    @Test(expected = ResourceException.class)
    public void resolveInvalidConicalPath() throws Exception {
        underTest.resolve("/\u0000test/parent", "child");
    }
}