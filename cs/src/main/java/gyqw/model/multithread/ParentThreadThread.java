package gyqw.model.multithread;

/**
 * @author fred
 * 2019-09-24 5:51 PM
 */
public class ParentThreadThread extends BaseThread {

    @Override
    public void run() {
        printCurrentThread("run");

        while (!interrupted()) {
            logger.info("while isInterrupted() %s", isInterrupted());
        }

        logger.info("interrupted() end %s", interrupted());
    }
}
