package gyqw.model.multithread;

import gyqw.util.Logger;

/**
 * @author fred
 * 2019-09-24 10:57 AM
 */
public class BaseThread extends Thread {
    protected Logger logger = new Logger();

    public BaseThread() {
        printCurrentThread("construct");
    }

    protected void printCurrentThread(String name) {
        logger.info("%s currentThread: %s", name, currentThread().getName());
        logger.info("%s thread name: %s", name, getName());
    }
}
