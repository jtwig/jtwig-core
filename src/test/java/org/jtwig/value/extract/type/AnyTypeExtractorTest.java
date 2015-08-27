package org.jtwig.value.extract.type;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigType;
import org.jtwig.value.Undefined;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AnyTypeExtractorTest {
    private AnyTypeExtractor underTest = new AnyTypeExtractor();

    @Test
    public void extractWhenNull() throws Exception {
        Optional<JtwigType> result = underTest.extract(null);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(JtwigType.ANY));
    }

    @Test
    public void extractWhenUndefined() throws Exception {
        Optional<JtwigType> result = underTest.extract(Undefined.UNDEFINED);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(JtwigType.ANY));
    }

    @Test
    public void extractWhenNumber() throws Exception {
        Optional<JtwigType> result = underTest.extract(1);

        assertThat(result.isPresent(), is(false));
    }
}