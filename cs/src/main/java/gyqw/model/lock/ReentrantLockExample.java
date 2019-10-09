package gyqw.model.lock;

import gyqw.util.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fred
 * 2019-10-09 11:28 AM
 */
public class ReentrantLockExample {
    private Logger logger = new Logger();
    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                logger.info(i);
            }
        } finally {
            // 确保释放锁，从而避免发生死锁
            lock.unlock();
        }
    }

}
