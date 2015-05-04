package org.jtwig.model.expression;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.expression.function.Argument;
import org.jtwig.model.position.Position;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.util.JtwigValue;

import java.util.*;

public class FunctionExpression extends InjectableExpression {
    private final String functionIdentifier;
    private final Arguments arguments;

    public FunctionExpression(Position position, String functionIdentifier, Collection<Argument> arguments) {
        super(position);
        this.functionIdentifier = functionIdentifier;
        this.arguments = new Arguments(arguments);
    }

    public String getFunctionIdentifier() {
        return functionIdentifier;
    }

    public Arguments getArguments() {
        return arguments;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        List<FunctionArgument> calculatedArguments = arguments.calculate(context);
        Optional<JtwigValue> value = context.configuration().functionResolver()
                .resolve(functionIdentifier, calculatedArguments)
                .transform(new Function<Supplier, JtwigValue>() {
                    @Override
                    public JtwigValue apply(Supplier input) {
                        return new JtwigValue(input.get());
                    }
                });

        if (!value.isPresent()) {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(getPosition(), String.format("Unable to resolve function '%s' with arguments %s", functionIdentifier, calculatedArguments)));
        } else {
            return value.get();
        }
    }

    @Override
    public Expression inject(Expression expression) {
        List<Argument> arguments = new ArrayList<>();
        arguments.add(new Argument(Optional.<String>absent(), expression));
        arguments.addAll(getArguments().arguments);
        return new FunctionExpression(getPosition(), functionIdentifier, arguments);
    }

    public static class Arguments implements Iterable<Argument> {
        private final LinkedList<Argument> arguments;

        public Arguments(Collection<Argument> arguments) {
            this.arguments = new LinkedList<>(arguments);
        }

        public int size() {
            return arguments.size();
        }

        public List<FunctionArgument> calculate(final RenderContext context) {
            return new ArrayList<>(Collections2.transform(arguments, new Function<Argument, FunctionArgument>() {
                @Override
                public FunctionArgument apply(Argument input) {
                    return input.calculate(context);
                }
            }));
        }

        @Override
        public Iterator<Argument> iterator() {
            return arguments.iterator();
        }
    }
}
