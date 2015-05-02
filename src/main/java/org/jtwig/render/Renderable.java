package org.jtwig.render;

import java.io.OutputStream;

public interface Renderable {
    void accept (OutputStream outputStream);
}
