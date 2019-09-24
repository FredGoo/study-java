package gyqw.model.multithread;

/**
 * @author fred
 * 2019-09-21 11:46 AM
 */
public class SleepThread extends BaseThread {

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            logger.info("myThread run");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
