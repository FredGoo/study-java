package gyqw.multithread;

import java.util.concurrent.Callable;

/**
 * @author fred
 * 2019-09-21 11:43 AM
 */
public class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 111;
    }
}
