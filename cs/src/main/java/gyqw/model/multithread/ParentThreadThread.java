package gyqw.model.multithread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-24 5:51 PM
 */
@Slf4j
public class ParentThreadThread extends BaseThread {

    @Override
    public void run() {
        printCurrentThread("run");

        while (!interrupted()) {
            log.info("while isInterrupted() {}", isInterrupted());
        }

        log.info("interrupted() end {}", interrupted());
    }
}
