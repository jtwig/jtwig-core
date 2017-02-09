package org.jtwig.resource.reference;

import org.jtwig.resource.reference.path.PathType;
import org.jtwig.resource.reference.path.PathTypeSupplier;

public class DefaultResourceReferenceExtractor implements ResourceReferenceExtractor {
    private final PathTypeSupplier pathTypeSupplier;
    private final PosixResourceReferenceExtractor posixResourceReferenceExtractor;
    private final UncResourceReferenceExtractor uncResourceReferenceExtractor;

    public DefaultResourceReferenceExtractor(PathTypeSupplier pathTypeSupplier, PosixResourceReferenceExtractor posixResourceReferenceExtractor, UncResourceReferenceExtractor uncResourceReferenceExtractor) {
        this.pathTypeSupplier = pathTypeSupplier;
        this.posixResourceReferenceExtractor = posixResourceReferenceExtractor;
        this.uncResourceReferenceExtractor = uncResourceReferenceExtractor;
    }

    @Override
    public ResourceReference extract(String spec) {
        if (pathTypeSupplier.get() == PathType.UNC) {
            return uncResourceReferenceExtractor.extract(spec);
        } else {
            return posixResourceReferenceExtractor.extract(spec);
        }
    }
}
