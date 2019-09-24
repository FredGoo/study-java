package gyqw.model.inherit;

import gyqw.util.Logger;

/**
 * @author fred
 * 2019-09-21 10:48 AM
 */
public class BabaObj extends YeyeObj {
    private static Logger logger = new Logger("static field baba");

    static {
        logger.info("static block baba");
    }

    {
        logger.info("block baba");
    }

    private PengyouObj pengyouObj = new PengyouObj("field baba");

    public BabaObj() {
        logger.info("construct baba");
    }
}
