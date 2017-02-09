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
}