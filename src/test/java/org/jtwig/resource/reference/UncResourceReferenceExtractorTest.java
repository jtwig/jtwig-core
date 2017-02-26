package org.jtwig.resource.reference;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UncResourceReferenceExtractorTest {
    private UncResourceReferenceExtractor underTest = new UncResourceReferenceExtractor();

    @Test
    public void extractWindows() throws Exception {
        String path = "C:\\file.jtwig";

        ResourceReference result = underTest.extract(path);

        assertThat(result.getType(), is(ResourceReference.ANY_TYPE));
        assertThat(result.getPath(), is(path));
    }

    @Test
    public void extractWindows2() throws Exception {
        String path = "F:\\file.jtwig";

        ResourceReference result = underTest.extract(path);

        assertThat(result.getType(), is(ResourceReference.ANY_TYPE));
        assertThat(result.getPath(), is(path));
    }

    @Test
    public void relativeNoType() throws Exception {
        ResourceReference result = underTest.extract("one\\test");

        assertThat(result.getType(), is(ResourceReference.ANY_TYPE));
        assertThat(result.getPath(), is("one\\test"));
    }

    @Test
    public void relativeWithType() throws Exception {
        ResourceReference result = underTest.extract("file:one\\test");

        assertThat(result.getType(), is(ResourceReference.FILE));
        assertThat(result.getPath(), is("one\\test"));
    }

    @Test
    public void absoluteWithType() throws Exception {
        ResourceReference result = underTest.extract("file:C:\\one\\test");

        assertThat(result.getType(), is(ResourceReference.FILE));
        assertThat(result.getPath(), is("C:\\one\\test"));
    }

    @Test
    public void emptyString() throws Exception {
        ResourceReference result = underTest.extract("");

        assertThat(result.getPath(), is(""));
    }

    @Test
    public void oneCharString() throws Exception {
        ResourceReference result = underTest.extract("a");

        assertThat(result.getPath(), is("a"));
    }

    @Test
    public void twoCharsString() throws Exception {
        ResourceReference result = underTest.extract("a:");

        assertThat(result.getPath(), is(""));
    }

    @Test
    public void twoCharsStringNoColon() throws Exception {
        ResourceReference result = underTest.extract("aa");

        assertThat(result.getPath(), is("aa"));
    }
}