package org.jtwig.resource.reference;

public class DefaultResourceReferenceExtractor implements ResourceReferenceExtractor {
    @Override
    public ResourceReference extract(String spec) {
        if (isWindows(spec)) {
            return new ResourceReference(ResourceReference.ANY_TYPE, spec);
        } else {
            int indexOf = spec.indexOf(":");
            if (indexOf == -1) {
                return new ResourceReference(ResourceReference.ANY_TYPE, spec);
            } else {
                return new ResourceReference(spec.substring(0, indexOf), spec.substring(indexOf + 1));
            }
        }
    }

    private boolean isWindows(String spec) {
        return spec.matches("^\\w:\\\\.*");
    }
}
