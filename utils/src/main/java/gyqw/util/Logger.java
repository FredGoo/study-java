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

    public void info(Object info) {
        System.out.println(info);
    }
}
