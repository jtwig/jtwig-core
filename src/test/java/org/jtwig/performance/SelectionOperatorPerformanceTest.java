package org.jtwig.performance;

import net.jperf.GroupedTimingStatistics;
import net.jperf.StopWatch;
import org.apache.commons.lang3.RandomStringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.expression.SelectionTest;
import org.junit.Test;

import static org.jtwig.JtwigModel.newModel;

public class SelectionOperatorPerformanceTest {
    public static final int SIZE = 100000;

    @Test
    public void select() throws Exception {
        GroupedTimingStatistics groupedTimingStatistics = new GroupedTimingStatistics();
        groupedTimingStatistics.setStartTime(System.currentTimeMillis());

        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{{ var.nested.field[0] }}");
        for (int i = 0; i < SIZE; i++) {
            StopWatch stopWatch = new StopWatch("selection");
            String random = RandomStringUtils.random(6);
            JtwigModel jtwigModel = newModel().with("var", new SelectionTest.TestClass(random));
            stopWatch.start();
            jtwigTemplate.render(jtwigModel);
            stopWatch.stop();
            groupedTimingStatistics.addStopWatch(stopWatch);
        }

        groupedTimingStatistics.setStopTime(System.currentTimeMillis());
        System.out.println(groupedTimingStatistics.toString());
    }
}
