package gyqw.model.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fred
 * 2019-10-09 11:28 AM
 */
@Slf4j
public class ReentrantLockExample {
    private Lock lock = new ReentrantLock();

    public void func() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                log.info(String.valueOf(i));
            }
        } finally {
            // 确保释放锁，从而避免发生死锁
            lock.unlock();
        }
    }

}
