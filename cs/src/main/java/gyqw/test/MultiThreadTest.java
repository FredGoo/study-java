package gyqw.test;

import gyqw.model.multithread.MyCallable;
import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

import java.util.concurrent.FutureTask;

/**
 * @author fred
 * 2019-09-24 10:24 AM
 */
public class MultiThreadTest {
    private Logger logger = new Logger();

    @Test
    public void test() throws Exception {
        MyCallable mc = new MyCallable();
        FutureTask<String> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();

        logger.info(ft.get());
    }
}
