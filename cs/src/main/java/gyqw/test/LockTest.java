package gyqw.test;

import gyqw.model.lock.ReentrantLockExample;
import gyqw.model.lock.SynchronizedExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author fred
 * 2019-10-09 2:06 PM
 */
@Slf4j
public class LockTest {

    @Test
    public void synchronizedTest() {
        SynchronizedExample e1 = new SynchronizedExample();
        SynchronizedExample e2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> e1.func1("e1"));
        executorService.execute(() -> e2.func1("e2"));
    }

    @Test
    public void reentrantLockTest() {
        ReentrantLockExample lockExample = new ReentrantLockExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(lockExample::func);
        executorService.execute(lockExample::func);
    }

    @Test
    public void countDownLatchTest() throws InterruptedException {
        final int totalThread = 10;
        CountDownLatch countDownLatch = new CountDownLatch(totalThread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = -1; i < totalThread; i++) {
            executorService.execute(() -> {
                log.info("run");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("end");
        executorService.shutdown();
    }

    @Test
    public void cyclicBarrierTest() {
        final int totalThread = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(totalThread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalThread; i++) {
            executorService.execute(() -> {
                log.info("run");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
                log.info("after");
            });
        }
        executorService.shutdown();
    }

    @Test
    public void semaphoreTest() {
        final int clientCount = 3;
        final int totalRequestCount = 10;
        Semaphore semaphore = new Semaphore(clientCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalRequestCount; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    log.info("availablePermits: {}", semaphore.availablePermits());
                } catch (InterruptedException e) {
                    log.error("InterruptedException", e);
                } finally {
                    semaphore.release();
                }
            });
        }
        executorService.shutdown();
    }

    @Test
    public void deadLockTest() {
        Object obj1 = new Object();
        Object obj2 = new Object();

        new Thread(() -> {
            synchronized (obj1) {
                log.info("{} get obj1", Thread.currentThread().getName());
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            log.info("{} waiting get obj2", Thread.currentThread().getName());
            synchronized (obj2) {
                log.info("%s get obj2", Thread.currentThread().getName());
            }
        }, "线程1").start();

        new Thread(() -> {
            synchronized (obj1) {
                log.info("{} get obj1", Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                log.info("%s waiting get obj2", Thread.currentThread().getName());
                synchronized (obj2) {
                    log.info("{} get obj2", Thread.currentThread().getName());
                }
            }
        }, "线程2").start();
    }
}
