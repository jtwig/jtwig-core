package org.jtwig.resource.reference;

public class PosixResourceReferenceExtractor implements ResourceReferenceExtractor {
    @Override
    public ResourceReference extract(String spec) {
        int indexOf = spec.indexOf(":");
        if (indexOf == -1) {
            return new ResourceReference(ResourceReference.ANY_TYPE, spec);
        } else {
            return new ResourceReference(spec.substring(0, indexOf), spec.substring(indexOf + 1));
        }
    }
}
