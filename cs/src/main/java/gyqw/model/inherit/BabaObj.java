package gyqw.model.inherit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-21 10:48 AM
 */
@Slf4j
public class BabaObj extends YeyeObj {

    static {
        log.info("static block baba");
    }

    {
        log.info("block baba");
    }

    private PengyouObj pengyouObj = new PengyouObj("field baba");

    public BabaObj() {
        log.info("construct baba");
    }
}
