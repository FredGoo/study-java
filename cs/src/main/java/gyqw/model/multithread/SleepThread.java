package gyqw.model.multithread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-21 11:46 AM
 */
@Slf4j
public class SleepThread extends BaseThread {

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            log.info("SleepThread run");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
