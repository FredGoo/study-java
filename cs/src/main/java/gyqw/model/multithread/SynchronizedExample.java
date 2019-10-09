package gyqw.model.multithread;

import gyqw.util.Logger;

/**
 * @author fred
 * 2019-10-09 11:09 AM
 */
public class SynchronizedExample {
    private Logger logger = new Logger();

    public void func1(String mark) {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                logger.info("mark: %s ,num: %s", mark, i);
            }
        }
    }
}
