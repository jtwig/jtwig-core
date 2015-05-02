package org.jtwig.context.values;

public interface ValueContextFactory {
    ValueContext create (ValueContext previous);
}
