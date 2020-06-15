package gyqw.test;

import gyqw.model.gc.OOMObject;
import gyqw.model.gc.SOFObject;
import gyqw.util.Logger;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fred
 * 2019-12-31 3:00 PM
 */
public class GcTest {
    private Logger logger = new Logger();
    private static final int _1MB = 1024 * 1024;

    @Test
    public void heapOOMTest() {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }

    @Test
    public void vmStackSOFTest() {
        SOFObject sofObject = new SOFObject(1);
        try {
            stackLeak(sofObject);
        } catch (Throwable e) {
            logger.info(sofObject.getLength());
        }
    }

    private void stackLeak(SOFObject sofObject) {
        sofObject.increment();
        stackLeak(sofObject);
    }

    @Test
    public void vmStackOOMTest() {
        while (true) {
            Thread thread = new Thread(() -> {
                OOMObject oomObject = new OOMObject();
                oomObject.dontStop();
            });
            thread.start();
        }
    }

    /**
     * 1.7以后常量池在堆里面，不会导致永久带或者metaspace溢出
     */
    @Test
    public void runtimeConstantPoolOOMTest() {
        List<String> stringList = new ArrayList<>();
        int i = 1;
        String s = "测试字符串";
        while (true) {
            s += i;
//            stringList.add(s.intern());
            stringList.add(String.valueOf(i).intern());
            i++;
        }
    }

    @Test
    public void methodAreaOOMTest() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, objects));
            enhancer.create();
        }
    }

    /**
     * -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    @Test
    public void allocationTest() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    @Test
    public void gcTest() {
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean b : l) {
            System.out.println(b.getName());
        }
    }
}
