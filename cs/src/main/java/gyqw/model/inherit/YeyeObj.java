package gyqw.model.inherit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-21 10:48 AM
 */
@Slf4j
public class YeyeObj {
    private PengyouObj pengyouObj = new PengyouObj("field yeye1");

    static {
        log.info("static block yeye");
    }

    {
        log.info("block yeye");
    }

    public YeyeObj() {
        log.info("construct yeye");
    }
}
