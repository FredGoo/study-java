package gyqw.model.classloader;

import gyqw.util.Logger;

/**
 * @author fred
 * 2020/3/14 8:29 下午
 */
public class DeadLoopModel {
    private static Logger logger = new Logger();

    static {
        if (true) {
            logger.info("%s init DeadLoopModel", Thread.currentThread());
            while (true) {
            }
        }
    }
}
