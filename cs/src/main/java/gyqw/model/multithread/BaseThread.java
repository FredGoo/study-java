package gyqw.model.multithread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-24 10:57 AM
 */
@Slf4j
public class BaseThread extends Thread {

    public BaseThread() {
        printCurrentThread("construct");
    }

    protected void printCurrentThread(String name) {
        log.info("{} currentThread: {}", name, currentThread().getName());
        log.info("{} thread name: {}", name, getName());
    }
}
