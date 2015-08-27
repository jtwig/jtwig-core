package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NumberTypeExtractorTest {
    private NumberTypeExtractor underTest = new NumberTypeExtractor();

    @Test
    public void extractWhenNonNumber() throws Exception {

        Optional<JtwigType> result = underTest.extract("asd");

        assertThat(result.isPresent(), is(false));
    }
}