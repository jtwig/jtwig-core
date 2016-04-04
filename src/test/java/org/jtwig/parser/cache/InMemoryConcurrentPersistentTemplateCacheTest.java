package org.jtwig.parser.cache;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.resource.Resource;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class InMemoryConcurrentPersistentTemplateCacheTest {
    private InMemoryConcurrentPersistentTemplateCache underTest = new InMemoryConcurrentPersistentTemplateCache();

    @Test
    public void multipleRequestsRetrievingBeforeCompleteParse() throws Exception {
        int parties = 100;
        final Node node = mock(Node.class);
        Semaphore semaphore = new Semaphore(0);
        ExecutorService executorService = new ThreadPoolExecutor(parties, parties, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        final CountDownLatch countDownLatch = new CountDownLatch(parties);
        AtomicInteger counter = new AtomicInteger(0);
        final JtwigParser jtwigParser = new ThreadedJtwigParser(counter, semaphore, node);
        final Resource resource = mock(Resource.class);

        for (int i = 0; i < parties; i++) {
            executorService.submit(new NamedRunnable(String.valueOf(i), new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                    Node result = underTest.get(resource, jtwigParser);
                    assertSame(result, node);
                }
            }));
        }

        countDownLatch.await();
        semaphore.release(parties);

        executorService.shutdown();
        assertThat(counter.get(), equalTo(1));

        Node result = underTest.get(resource, jtwigParser);

        assertSame(result, node);
        assertThat(counter.get(), equalTo(1));
    }

    private static class NamedRunnable implements Runnable {
        private final String name;
        private final Runnable runnable;

        private NamedRunnable(String name, Runnable runnable) {
            this.name = name;
            this.runnable = runnable;
        }

        @Override
        public void run() {
            this.runnable.run();
        }
    }

    private static class ThreadedJtwigParser implements JtwigParser {
        private final AtomicInteger counter;
        private final Semaphore semaphore;
        private final Node node;

        private ThreadedJtwigParser(AtomicInteger counter, Semaphore semaphore, Node node) {
            this.counter = counter;
            this.semaphore = semaphore;
            this.node = node;
        }

        @Override
        public Node parse(Resource resource) {
            try {
                semaphore.acquire();
                counter.incrementAndGet();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return node;
        }
    }
}