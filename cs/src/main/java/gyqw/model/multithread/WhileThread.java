package gyqw.model.multithread;

/**
 * @author fred
 * 2019-09-24 10:56 AM
 */
public class WhileThread extends BaseThread {

    @Override
    public void run() {
        int i = 0;
        logger.info("WhileThread loop " + i + " " + isInterrupted());

        while (!interrupted()) {
            i++;
            logger.info("WhileThread loop " + i + " " + isInterrupted());
        }
        logger.info("WhileThread end " + isInterrupted());
    }
}
