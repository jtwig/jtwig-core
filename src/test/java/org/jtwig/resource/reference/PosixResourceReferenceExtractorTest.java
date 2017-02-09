package org.jtwig.resource.reference;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PosixResourceReferenceExtractorTest {
    private PosixResourceReferenceExtractor underTest = new PosixResourceReferenceExtractor();

    @Test
    public void extractUnix() throws Exception {
        String path = "/path/location";

        ResourceReference result = underTest.extract(path);

        assertThat(result.getType(), is(ResourceReference.ANY_TYPE));
        assertThat(result.getPath(), is(path));

    }
}