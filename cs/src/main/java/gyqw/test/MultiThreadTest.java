package gyqw.test;

import gyqw.model.multithread.ParentThreadThread;
import gyqw.model.multithread.SleepThread;
import gyqw.model.multithread.WhileThread;
import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fred
 * 2019-09-24 10:24 AM
 */
public class MultiThreadTest {
    private Logger logger = new Logger();

    @Test
    public void sleepInterrupt() {
        SleepThread myThread = new SleepThread();
        myThread.start();
        myThread.interrupt();
    }


    @Test
    public void whileInterrupt() throws InterruptedException {
        WhileThread whileThread = new WhileThread();
        whileThread.start();
        Thread.sleep(1);
        whileThread.interrupt();
    }

    @Test
    public void executorShutdown() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WhileThread());
        Thread.sleep(1);
        List<Runnable> runnableList = executorService.shutdownNow();

        while (!executorService.isTerminated()) {
        }
    }

    @Test
    public void parentThreadInterrupt() throws InterruptedException {
        ParentThreadThread parentThreadThread = new ParentThreadThread();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(parentThreadThread);
        Thread.sleep(1);
        executorService.shutdownNow();
    }
}
