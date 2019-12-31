package gyqw.test;

import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author fred
 * 2019-12-31 3:00 PM
 */
public class GcTest {
    private Logger logger = new Logger();

    @Test
    public void allocationTest() {
        byte[] allocation1, allocation2;
        allocation1 = new byte[30900 * 1024];
        allocation2 = new byte[30900 * 1024];
    }
}
