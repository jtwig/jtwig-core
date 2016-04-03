package org.jtwig.value.convert;

import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.util.ErrorMessageFormatter;

public interface Converter<T> {
    Result<T> convert(Object object);

    class Result<T> {
        public static <T> Result<T> undefined () {
            return new Result<>(null, false);
        }

        public static <T> Result<T> defined (T value) {
            return new Result<>(value, true);
        }

        private final T value;
        private final boolean defined;

        public Result(T value, boolean defined) {
            this.value = value;
            this.defined = defined;
        }

        public T get() {
            return value;
        }

        public boolean isDefined() {
            return defined;
        }

        public T or(T otherwise) {
            if (defined) return value;
            else return otherwise;
        }

        public T orThrow(Position position, String message) {
            if (defined) return value;
            throw new CalculationException(ErrorMessageFormatter.errorMessage(position, message));
        }
    }
}
