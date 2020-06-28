package gyqw.model.multithread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2019-09-24 10:56 AM
 */
@Slf4j
public class WhileThread extends BaseThread {

    @Override
    public void run() {
        printCurrentThread("run");

        int i = 0;
        log.info("WhileThread loop {} {}", i, interrupted());

        while (!interrupted()) {
            i++;
            log.info("WhileThread loop {}", i);
        }

        log.info("WhileThread end");
    }
}
