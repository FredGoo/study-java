package gyqw.util;

/**
 * @author fred
 * 2019-09-21 10:54 AM
 */
public class Logger {

    public Logger() {
    }

    public Logger(String marker) {
        info(marker);
    }

    public static void info(Object info) {
        System.out.println(info);
    }

    public static void info(String info, Object... param) {
        System.out.println(String.format(info, param));
    }

    public static void error(Throwable e) {
        e.printStackTrace();
    }
}
