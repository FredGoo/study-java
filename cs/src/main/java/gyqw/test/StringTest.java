package gyqw.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2019-12-31 2:24 PM
 */
@Slf4j
public class StringTest {

    @Test
    public void catTest() {
        String s1 = "cat";
        String s2 = "cat";
        String s3 = new String("cat");
        String s4 = new String("cat");

        log.info("s1 == s2: {}", s1 == s2);
        log.info("s1 == s3: {}", s1 == s3);
        log.info("s3 == s4: {}", s3 == s4);
    }

    @Test
    public void internTest() {
        String s1 = "java";
        String s2 = new StringBuilder("ja").append("va1").toString();
        log.info("s2 == s2.intern(): {}", s2 == s2.intern());
    }
}
