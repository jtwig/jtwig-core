package org.jtwig.resource.reference;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResourceReferenceTest {

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