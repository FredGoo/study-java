package gyqw.test;

import gyqw.model.classloader.DeadLoopModel;
import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2020-01-21 3:06 PM
 */
public class ClassLoaderTest {
    private Logger logger = new Logger();

    @Test
    public void parentLoaderTest() {
        logger.info("ClassLoaderTest's ClassLoader is: %s", ClassLoaderTest.class.getClassLoader());
        logger.info("The Parent of ClassLoaderTest's ClassLoader is: %s", ClassLoaderTest.class.getClassLoader().getParent());
        logger.info("The GrandParent of ClassLoaderTest's ClassLoader is: %s", ClassLoaderTest.class.getClassLoader().getParent().getParent());
    }

    @Test
    public void deadLoopTest() {
        Runnable script = () -> {
            logger.info("%s start", Thread.currentThread());
            DeadLoopModel dlm = new DeadLoopModel();
            logger.info("%s run over", Thread.currentThread());
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
