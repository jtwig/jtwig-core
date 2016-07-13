package org.jtwig.functions.config;

import org.jtwig.functions.JtwigFunction;
import org.jtwig.functions.impl.control.EscapeFunction;
import org.jtwig.functions.impl.control.RawFunction;
import org.jtwig.functions.impl.date.DateFormatFunction;
import org.jtwig.functions.impl.java.ConstantFunction;
import org.jtwig.functions.impl.list.*;
import org.jtwig.functions.impl.logical.*;
import org.jtwig.functions.impl.map.KeysFunction;
import org.jtwig.functions.impl.math.AbsFunction;
import org.jtwig.functions.impl.math.RoundFunction;
import org.jtwig.functions.impl.mixed.*;
import org.jtwig.functions.impl.string.*;
import org.jtwig.functions.impl.structural.BlockFunction;
import org.jtwig.util.ClasspathFinder;

import java.util.ArrayList;
import java.util.Arrays;

public class DefaultJtwigFunctionList extends ArrayList<JtwigFunction> {
    public DefaultJtwigFunctionList() {
        super(Arrays.<JtwigFunction>asList(
                // Logical
                new DefinedFunction(),
                new EvenFunction(),
                new OddFunction(),
                new IterableFunction(),
                new EmptyFunction(),

                // Control
                new EscapeFunction(),
                new RawFunction(),

                // Structural
                new BlockFunction(),

                // Math
                new AbsFunction(),
                new RoundFunction(),

                // String
                new NumberFormatFunction(),
                new CapitalizeFunction(),
                new ConvertEncodingFunction(),
                new FormatFunction(),
                new LowerFunction(),
                new NlToBrFunction(),
                new ReplaceFunction(),
                new SplitFunction(),
                new StripTagsFunction(),
                new TitleFunction(),
                new TrimFunction(),
                new UpperFunction(),

                // Mixed
                new ConstantFunction(new ClasspathFinder(ConstantFunction.class.getClassLoader())),
                new UrlEncodeFunction(),
                new FirstFunction(),
                new LastFunction(),
                new ReverseFunction(),
                new DefaultFunction(),
                new LengthFunction(),

                // Map
                new KeysFunction(),

                // List
                new BatchFunction(),
                new ConcatenateFunction(),
                new JoinFunction(),
                new MergeFunction(),
                new SliceFunction(),
                new SortFunction(),
                new RangeFunction(),

                // Date
                new DateFormatFunction()
        ));
    }
}
