package org.jtwig.resource.reference;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceReferenceTest {
    @Test
    public void fileAsString() throws Exception {
        ResourceReference underTest = ResourceReference.file("path");

        assertThat(underTest.getType(), is("file"));
        assertThat(underTest.getPath(), is("path"));
    }
    @Test
    public void fileAsFile() throws Exception {
        File path = new File("/test");
        ResourceReference underTest = ResourceReference.file(path);

        assertThat(underTest.getType(), is("file"));
        assertThat(underTest.getPath(), is(path.getAbsolutePath()));
    }
    @Test
    public void memory() throws Exception {
        ResourceReference underTest = ResourceReference.memory("name");

        assertThat(underTest.getType(), is("memory"));
        assertThat(underTest.getPath(), is("name"));
    }
    @Test
    public void classpath() throws Exception {
        ResourceReference underTest = ResourceReference.classpath("name");

        assertThat(underTest.getType(), is("classpath"));
        assertThat(underTest.getPath(), is("name"));
    }
    @Test
    public void inline() throws Exception {
        ResourceReference underTest = ResourceReference.inline("template");

        assertThat(underTest.getType(), is("string"));
        assertThat(underTest.getPath(), is("template"));
    }

    @Test
    public void equalsSameInstance() throws Exception {
        ResourceReference instance = new ResourceReference("type", "path");

        assertThat(instance.equals(instance), is(true));
    }

    @Test
    public void equalsDistinctTypes() throws Exception {
        ResourceReference instance = new ResourceReference("type", "path");

        assertThat(instance.equals(new Object()), is(false));
    }

    @Test
    public void toStringAny() throws Exception {
        ResourceReference instance = new ResourceReference("any", "path");

        assertThat(instance.toString(), is("path"));
    }
}