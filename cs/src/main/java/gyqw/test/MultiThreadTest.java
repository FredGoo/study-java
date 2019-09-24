package gyqw.test;

import gyqw.model.multithread.SleepThread;
import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

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
    public void whileInterrupt() {

    }
}
