package org.jtwig.value.extract.number;

import com.google.common.base.Optional;
import org.jtwig.value.Undefined;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NullNumberExtractorTest {
    private NullNumberExtractor underTest = new NullNumberExtractor();

    @Test
    public void extractWhenUndefined() throws Exception {
        Optional<BigDecimal> result = underTest.extract(Undefined.UNDEFINED);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(BigDecimal.ZERO));
    }

    @Test
    public void extractWhenNull() throws Exception {
        Optional<BigDecimal> result = underTest.extract(null);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(BigDecimal.ZERO));
    }
}