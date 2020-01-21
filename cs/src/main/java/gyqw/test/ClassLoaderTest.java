package gyqw.test;

import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2020-01-21 3:06 PM
 */
public class ClassLoaderTest {
    private Logger logger = new Logger();

    @Test
    public void parentLoader() {
        logger.info("ClassLoaderTest's ClassLoader is: %s", ClassLoaderTest.class.getClassLoader());
        logger.info("The Parent of ClassLoaderTest's ClassLoader is: %s", ClassLoaderTest.class.getClassLoader().getParent());
        logger.info("The GrandParent of ClassLoaderTest's ClassLoader is: %s", ClassLoaderTest.class.getClassLoader().getParent().getParent());
    }
}
