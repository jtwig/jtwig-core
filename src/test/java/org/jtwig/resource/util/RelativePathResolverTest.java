package org.jtwig.resource.util;

import org.jtwig.resource.exceptions.ResourceException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RelativePathResolverTest {
    private final File file = mock(File.class);
    private RelativePathResolver underTest = new RelativePathResolver();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void resolveWhenCannotGetCanonicalPath() throws Exception {
        underTest = new RelativePathResolver() {
            @Override
            protected File newFile(File parentFile, String relativePath) {
                return file;
            }
        };
        when(file.getCanonicalPath()).thenThrow(IOException.class);

        expectedException.expect(ResourceException.class);
        expectedException.expectMessage(containsString("Unable to get canonical path for resource 'two' relative to '/one'"));

        underTest.resolve("/one", "two");

    }

    @Test
    public void resolveHappy() throws Exception {
        String result = underTest.resolve("/one", "two");

        assertThat(result, is("/two"));
    }
}