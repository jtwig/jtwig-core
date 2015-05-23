package org.jtwig.model.tree;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import org.jtwig.context.RenderContext;
import org.jtwig.context.values.ValueContext;
import org.jtwig.i18n.decorate.CompositeMessageDecorator;
import org.jtwig.i18n.decorate.ExpressionMessageDecorator;
import org.jtwig.i18n.decorate.MessageDecorator;
import org.jtwig.i18n.decorate.ReplacementMessageDecorator;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.reflection.model.Value;
import org.jtwig.render.Renderable;
import org.jtwig.render.StringBuilderRenderResult;
import org.jtwig.render.impl.StringRenderable;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.jtwig.value.configuration.ValueConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public class TranslateNode extends ContentNode {
    private final Optional<Expression> withExpression;
    private final Optional<Expression> localeExpression;

    public TranslateNode(Position position, Node content, Expression withExpression, Expression localeExpression) {
        super(position, content);
        this.withExpression = Optional.fromNullable(withExpression);
        this.localeExpression = Optional.fromNullable(localeExpression);
    }

    @Override
    public Renderable render(RenderContext context) {
        String message = super.render(context)
                .appendTo(new StringBuilderRenderResult())
                .content().trim();

        Locale locale;
        if (localeExpression.isPresent()) {
            JtwigValue calculate = localeExpression.get().calculate(context);
            if (calculate.isInstanceOf(Locale.class)) {
                locale = calculate.as(Locale.class);
            } else {
                locale = context.environment().renderConfiguration().localeResolver().resolve(calculate.asString());
            }
        } else {
            locale = context.environment().renderConfiguration().currentLocaleSupplier().get();
        }

        Collection<MessageDecorator> decorators = new ArrayList<>();

        ValueConfiguration valueConfiguration = context.environment().valueConfiguration();
        if (withExpression.isPresent()) {
            decorators.add(new ReplacementMessageDecorator(toReplacements(withExpression.get().calculate(context).asMap(), valueConfiguration)));
        }
        decorators.add(new ExpressionMessageDecorator(fromContextValue(context.valueContext(), valueConfiguration)));

        CompositeMessageDecorator messageDecorator = new CompositeMessageDecorator(decorators);
        return new StringRenderable(context.environment().messageResolver()
                .resolve(locale, message, messageDecorator)
                .or(defaultMessage(message, messageDecorator)), context.escapeContext().currentEscapeMode());
    }

    private Collection<ReplacementMessageDecorator.Replacement> toReplacements(Map<Object, Object> objectObjectMap, ValueConfiguration valueConfiguration) {
        Collection<ReplacementMessageDecorator.Replacement> result = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : objectObjectMap.entrySet()) {
            Object key = entry.getKey();
            if (key != null) {
                String replace = JtwigValueFactory.value(entry.getValue(), valueConfiguration).asString();
                result.add(new ReplacementMessageDecorator.Replacement(key.toString(), replace));
            }
        }
        return result;
    }

    private ExpressionMessageDecorator.ReplacementFinder fromContextValue(final ValueContext valueContext, final ValueConfiguration configuration) {
        return new ExpressionMessageDecorator.ReplacementFinder() {
            @Override
            public String replacementFor(String key) {
                return JtwigValueFactory.value(valueContext.value(key)
                        .or(new Value("")).getValue(), configuration)
                        .asString();
            }
        };
    }

    private Supplier<String> defaultMessage(final String message, final CompositeMessageDecorator messageDecorator) {
        return new Supplier<String>() {
            @Override
            public String get() {
                return messageDecorator.decorate(message);
            }
        };
    }
}
