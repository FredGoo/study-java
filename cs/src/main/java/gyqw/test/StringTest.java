package gyqw.test;

import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2019-12-31 2:24 PM
 */
public class StringTest {
    private Logger logger = new Logger();

    @Test
    public void catTest() {
        String s1 = "cat";
        String s2 = "cat";
        String s3 = new String("cat");
        String s4 = new String("cat");

        logger.info(s1 == s2);
        logger.info(s1 == s3);
        logger.info(s3 == s4);
    }
}
