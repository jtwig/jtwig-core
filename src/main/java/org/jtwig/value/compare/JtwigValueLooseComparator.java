package org.jtwig.value.compare;

import com.google.common.base.Optional;
import org.jtwig.value.JtwigValue;

import java.math.BigDecimal;
import java.util.Comparator;

public class JtwigValueLooseComparator implements Comparator<JtwigValue> {
    private final static JtwigValueLooseComparator instance = new JtwigValueLooseComparator();

    public static JtwigValueLooseComparator instance () {
        return instance;
    }


    private JtwigValueLooseComparator() {
    }

    @Override
    public int compare(JtwigValue left, JtwigValue right) {
        if (left == null && right == null) {
            return 0;
        } else if (left != null && right == null) {
            return 1;
        } else if (left == null) {
            return -1;
        }

        Optional<BigDecimal> leftNumber = left.asNumber();
        Optional<BigDecimal> rightNumber = right.asNumber();

        if (leftNumber.isPresent() && !rightNumber.isPresent()) {
            return 1;
        } else if (!leftNumber.isPresent() && rightNumber.isPresent()) {
            return -1;
        } else if (leftNumber.isPresent() && rightNumber.isPresent()) {
            return leftNumber.get().compareTo(rightNumber.get());
        }

        return left.asObject().toString().compareTo(right.asObject().toString());
    }
}
