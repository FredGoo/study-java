package gyqw.inherit;

import gyqw.util.Logger;

/**
 * @author fred
 * 2019-09-21 10:48 AM
 */
public class YeyeObj {
    private static Logger logger = new Logger("static field yeye");
    private PengyouObj pengyouObj = new PengyouObj("field yeye1");

    static {
        logger.info("static block yeye");
    }

    {
        logger.info("block yeye");
    }

    public YeyeObj() {
        logger.info("construct yeye");
    }
}
