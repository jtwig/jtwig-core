package org.jtwig.value.compare;

import org.jtwig.render.RenderRequest;

public interface ValueComparator {
    int compare(RenderRequest renderRequest, Object left, Object right);
}
