package gyqw.model.classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fred
 * 2020/3/14 8:29 下午
 */
@Slf4j
public class DeadLoopModel {

    static {
        if (true) {
            log.info("{} init DeadLoopModel", Thread.currentThread());
            while (true) {
            }
        }
    }
}
