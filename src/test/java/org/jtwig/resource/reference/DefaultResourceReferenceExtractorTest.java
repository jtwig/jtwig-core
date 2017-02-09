package org.jtwig.resource.reference;

import org.jtwig.resource.reference.path.PathType;
import org.jtwig.resource.reference.path.PathTypeSupplier;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DefaultResourceReferenceExtractorTest {
    private final PathTypeSupplier pathTypeSupplier = mock(PathTypeSupplier.class);
    private final PosixResourceReferenceExtractor posixResourceReferenceExtractor = mock(PosixResourceReferenceExtractor.class);
    private final UncResourceReferenceExtractor uncResourceReferenceExtractor = mock(UncResourceReferenceExtractor.class);
    private DefaultResourceReferenceExtractor underTest = new DefaultResourceReferenceExtractor(pathTypeSupplier, posixResourceReferenceExtractor, uncResourceReferenceExtractor);

    @Test
    public void posix() throws Exception {
        String spec = "spec";

        ResourceReference resourceReference = mock(ResourceReference.class);

        given(pathTypeSupplier.get()).willReturn(PathType.POSIX);
        given(posixResourceReferenceExtractor.extract(spec)).willReturn(resourceReference);

        ResourceReference result = underTest.extract(spec);

        assertSame(resourceReference, result);
        verifyZeroInteractions(uncResourceReferenceExtractor);
    }

    @Test
    public void unc() throws Exception {
        String spec = "spec";

        ResourceReference resourceReference = mock(ResourceReference.class);

        given(pathTypeSupplier.get()).willReturn(PathType.UNC);
        given(uncResourceReferenceExtractor.extract(spec)).willReturn(resourceReference);

        ResourceReference result = underTest.extract(spec);

        assertSame(resourceReference, result);
        verifyZeroInteractions(posixResourceReferenceExtractor);
    }
}