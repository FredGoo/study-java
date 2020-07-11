package gyqw.model.inherit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-21 10:48 AM
 */
@Slf4j
public class Erziobj extends BabaObj {

    static {
        log.info("static block erzi");
    }

    {
        log.info("block erzi");
    }

    public Erziobj() {
        log.info("construct erzi");
    }
}
