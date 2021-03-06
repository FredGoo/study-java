package gyqw.test;

import gyqw.model.multithread.ParentThreadThread;
import gyqw.model.multithread.SleepThread;
import gyqw.model.multithread.ThreadUnsafeExample;
import gyqw.model.multithread.WhileThread;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fred
 * 2019-09-24 10:24 AM
 */
@Slf4j
public class MultiThreadTest {

    /**
     * 制造InterruptedException异常
     * 当线程sleep的时候不能中断
     */
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

    /**
     * currentThread中断测试
     * interrupted方法返回的是父线程的中断状态，切会清除父进程的状态
     * isInterrupted方法返回的是当前线程的中断状态，不会清除状态
     *
     * @throws InterruptedException
     */
    @Test
    public void whileInterruptCurrentThread() throws InterruptedException {
        log.info(Thread.currentThread().getName());

        WhileThread whileThread = new WhileThread();
        Thread thread = new Thread(whileThread);

        thread.start();
        Thread.sleep(1);
        thread.interrupt();
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

    /**
     * 多个线程同时操作一个对象，没有上锁
     *
     * @throws InterruptedException 中断异常
     */
    @Test
    public void threadUnsafeTest() throws InterruptedException {
        final int threadSize = 1000;
        ThreadUnsafeExample threadUnsafeExample = new ThreadUnsafeExample();
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                threadUnsafeExample.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("threadUnsafeExample: {}", threadUnsafeExample.get());
    }
}
