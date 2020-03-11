package gyqw.test;

import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2020-03-11 3:42 PM
 */
public class Yeqinwen {
    private Logger logger = new Logger();

    @Test
    public void charTest() {
        int x =25508;
        char y = (char) x;
        logger.info(y);
    }
}
