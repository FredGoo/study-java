package gyqw.test;

import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author fred
 * 2019-10-21 3:32 PM
 */
public class ThreadTest {
    private Logger logger = new Logger();

    @Test
    public void mxBean() {
        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            logger.info("[%s] %s", threadInfo.getThreadId(), threadInfo.getThreadName());
        }
    }
}
