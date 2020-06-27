package gyqw.test;

import gyqw.model.classloader.DeadLoopModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2020-01-21 3:06 PM
 */
@Slf4j
public class ClassLoaderTest {

    @Test
    public void parentLoaderTest() {
        log.info("ClassLoaderTest's ClassLoader is: {}", ClassLoaderTest.class.getClassLoader());
        log.info("The Parent of ClassLoaderTest's ClassLoader is: {}", ClassLoaderTest.class.getClassLoader().getParent());
        log.info("The GrandParent of ClassLoaderTest's ClassLoader is: {}", ClassLoaderTest.class.getClassLoader().getParent().getParent());
    }

    @Test
    public void deadLoopTest() {
        Runnable script = () -> {
            log.info("{} start", Thread.currentThread());
            DeadLoopModel dlm = new DeadLoopModel();
            log.info("{} run over", Thread.currentThread());
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
