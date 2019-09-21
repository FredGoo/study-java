package gyqw.multithread;

import gyqw.util.Logger;

import java.util.concurrent.FutureTask;

/**
 * @author fred
 * 2019-09-21 11:42 AM
 */
public class MultiThreadMain {
    private static Logger logger = new Logger();

    public static void main(String[] args) throws Exception {
        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();

        logger.info(ft.get());
    }
}
