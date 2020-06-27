package gyqw.model.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-10-09 11:09 AM
 */
@Slf4j
public class SynchronizedExample {

    public void func1(String mark) {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                log.info("mark: {} ,num: {}", mark, i);
            }
        }
    }
}
