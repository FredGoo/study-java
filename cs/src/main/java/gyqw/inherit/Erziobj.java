package gyqw.inherit;

import gyqw.util.Logger;

/**
 * @author fred
 * 2019-09-21 10:48 AM
 */
public class Erziobj extends BabaObj {
    private static Logger logger = new Logger("static field erzi");

    static {
        logger.info("static block erzi");
    }

    {
        logger.info("block erzi");
    }

    public Erziobj() {
        logger.info("construct erzi");
    }
}
