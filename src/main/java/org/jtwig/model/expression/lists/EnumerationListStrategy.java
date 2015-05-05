package org.jtwig.model.expression.lists;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

import java.util.Collection;

public interface EnumerationListStrategy {
    Optional<Collection<Object>> enumerate (JtwigValue left, JtwigValue right);
}
