package org.jtwig.render;

public interface RenderResult {
    RenderResult append(String content);
    RenderResult flush ();
    String content ();
}
