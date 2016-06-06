package org.jtwig.integration.resources;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.resource.exceptions.ResourceNotFoundException;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class RetrieveNonexistentResourceMultipleTimesTest {
    private static ExecutorService service = Executors.newSingleThreadExecutor();

    @Test
    public void test() throws Exception {
        File tempFile = File.createTempFile("non-existing", "file");
        if (tempFile.exists())
            if (!tempFile.delete()) throw new RuntimeException("Cannot setup test environment! BOOOOM");

        final JtwigTemplate jtwigTemplate = JtwigTemplate.fileTemplate(tempFile);

        service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    jtwigTemplate.render(JtwigModel.newModel());
                    fail("Expected exception");
                } catch (Exception e) {
                }
                return null;
            }
        }).get(10, TimeUnit.SECONDS);

        service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    return jtwigTemplate.render(JtwigModel.newModel());
                } catch (ResourceNotFoundException e) {

                    return null;
                } catch (Exception e) {
                    throw e;
                }
            }
        }).get(10, TimeUnit.SECONDS);
    }
}
